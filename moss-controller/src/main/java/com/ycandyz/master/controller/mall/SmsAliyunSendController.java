package com.ycandyz.master.controller.mall;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.ycandyz.master.aliyun.sms.AliSmsMessage;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.dao.organize.OrganizeDao;
import com.ycandyz.master.dao.sms.SmsSendQueueLogDao;
import com.ycandyz.master.dto.organize.OrganizeDTO;
import com.ycandyz.master.entities.sms.SmsSendQueueLog;
import com.ycandyz.master.kafka.KafkaProducer;
import com.ycandyz.master.kafka.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sms")
@Slf4j
public class SmsAliyunSendController {

    @Value("${aliyun.sms.service-number}")
    private String serviceNumber;
    //对应模板代码
    @Value("${aliyun.sms.template_code}")
    private String templateCode;

    @Value("${topic.sms}")
    private String topicId;

    @Value("${spring.profiles.active}")
    private String profileActive;

    @Autowired
    private OrganizeDao organizeDao;

    @Autowired
    private AliSmsMessage aliSmsMessage;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private SmsSendQueueLogDao smsSendQueueLogDao;

    @GetMapping("/send/msg")
    public ReturnResponse<String> sendShortMsg(){
        smsSendToday();
        smsSendSevenDay();
        smsSendThirtyDay();
        return ReturnResponse.success("成功");
    }

    /**
     * u客用户发送短信，到期提醒，今天
     */
    private void smsSendToday() {

        List<String> phone = Arrays.asList("13715249609","17666013575","15986727355","18923798099","15920087843","13522809059","15652228052");

        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
        JSONArray jsonArray = null;
        //当天
        Long serviceNowBegin = DateUtil.beginOfDay(new Date()).getTime()/1000;
        Long serviceNowEnd = DateUtil.endOfDay(new Date()).getTime()/1000;
        List<OrganizeDTO> list = organizeDao.queryByServiceTime(serviceNowBegin,serviceNowEnd);
        if (list!=null && list.size()>0){
            for (OrganizeDTO organizeDTO : list){
                if (profileActive.equals("dev") || profileActive.equals("test")){
                    if (phone.contains(organizeDTO.getPhone())){
                        List<Object> phoneList = new ArrayList<>();
                        jsonArray = new JSONArray();
                        phoneList.add(organizeDTO.getPhone());
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("name",organizeDTO.getFullNname());
                        jsonObject.put("msg","今日");
                        jsonArray.add(jsonObject);

                        phoneList.add(serviceNumber);
                        JSONObject jsonObjectService = new JSONObject();
                        jsonObjectService.put("name",organizeDTO.getFullNname());
                        jsonObjectService.put("msg","今日");
                        jsonArray.add(jsonObjectService);
                        String bizId = aliSmsMessage.sendMsg(phoneList,jsonArray.toJSONString(),templateCode);
                        if (bizId!=null && !"".equals(bizId)) {
                            List<QuerySendDetailsResponse.SmsSendDetailDTO> responseList = aliSmsMessage.messageSendState(phoneList, bizId);
                            insertMysql(responseList,bizId);
                        }
                    }
                }
            }
        }
    }

    private void insertMysql(List<QuerySendDetailsResponse.SmsSendDetailDTO> responseList, String bizId){
        if (responseList!=null && responseList.size()>0){
            for (QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : responseList){
                SmsSendQueueLog smsSendQueueLog = new SmsSendQueueLog();
                smsSendQueueLog.setBizId(bizId);
                smsSendQueueLog.setCreatedAt(System.currentTimeMillis()/1000);
                smsSendQueueLog.setLog(smsSendDetailDTO.getErrCode());
                smsSendQueueLog.setPhone(smsSendDetailDTO.getPhoneNum());
                smsSendQueueLog.setTemplateCode(smsSendDetailDTO.getTemplateCode());
                if (smsSendDetailDTO.getSendStatus()==3){   //短信发送成功
                    //记录数据库
                    smsSendQueueLog.setState(1);
                }else if (smsSendDetailDTO.getSendStatus()==2){ //发送失败
                    //记录数据库
                    smsSendQueueLog.setState(2);
                    //发送失败，提交kafka，准备二次发送
                    Message message = new Message();
                    message.setId(topicId+"#"+ UUID.randomUUID().toString());
                    message.setTime(System.currentTimeMillis());
                    message.setMsg(smsSendQueueLog);
                    kafkaProducer.send(message,topicId);
                }else if (smsSendDetailDTO.getSendStatus()==1){ //等待回执
                    //记录数据库
                    smsSendQueueLog.setState(0);
                }
                smsSendQueueLogDao.insert(smsSendQueueLog);
            }
        }
    }

    /**
     * u客用户发送短信，到期提醒，前七天
     */
    private void smsSendSevenDay() {

        List<String> phone = Arrays.asList("13715249609","17666013575","15986727355","18923798099","15920087843","13522809059","15652228052");

        JSONArray jsonArray = null;
        //七天
        DateTime sevenDateTime = DateUtil.offset(new Date(), DateField.DAY_OF_YEAR, 7);
        Long serviceSevenBegin = DateUtil.beginOfDay(sevenDateTime).getTime()/1000;
        Long serviceSevenEnd = DateUtil.endOfDay(sevenDateTime).getTime()/1000;
        List<OrganizeDTO> sevenList = organizeDao.queryByServiceTime(serviceSevenBegin,serviceSevenEnd);
        if (sevenList!=null && sevenList.size()>0){
            String msg = DateUtil.format(DateUtil.offsetDay(new Date(), 7),"yyyy年MM月dd日");
            for (OrganizeDTO organizeDTO : sevenList){
                if (profileActive.equals("dev") || profileActive.equals("test")){
                    if (phone.contains(organizeDTO.getPhone())){
                        List<Object> sevenPhoneList = new ArrayList<>();
                        jsonArray = new JSONArray();
                        sevenPhoneList.add(organizeDTO.getPhone());
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("name",organizeDTO.getFullNname());
                        jsonObject.put("msg",msg);
                        jsonArray.add(jsonObject);

                        sevenPhoneList.add(serviceNumber);
                        JSONObject jsonObjectService = new JSONObject();
                        jsonObjectService.put("name",organizeDTO.getFullNname());
                        jsonObjectService.put("msg",msg);
                        jsonArray.add(jsonObjectService);
                        String bizId = aliSmsMessage.sendMsg(sevenPhoneList,jsonArray.toJSONString(),templateCode);
                        if (bizId!=null && !"".equals(bizId)) {
                            List<QuerySendDetailsResponse.SmsSendDetailDTO> responseList = aliSmsMessage.messageSendState(sevenPhoneList, bizId);
                            insertMysql(responseList,bizId);
                        }
                    }
                }
            }
        }
    }

    /**
     * u客用户发送短信，到期提醒，前30天
     */
    private void smsSendThirtyDay() {

        List<String> phone = Arrays.asList("13715249609","17666013575","15986727355","18923798099","15920087843","13522809059","15652228052");

        JSONArray jsonArray = null;
        //30天
        DateTime thirtyDateTime = DateUtil.offset(new Date(), DateField.DAY_OF_YEAR, 30);
        Long serviceThirtyBegin = DateUtil.beginOfDay(thirtyDateTime).getTime()/1000;
        Long serviceThirtyEnd = DateUtil.endOfDay(thirtyDateTime).getTime()/1000;
        List<OrganizeDTO> thirtyList = organizeDao.queryByServiceTime(serviceThirtyBegin,serviceThirtyEnd);
        if (thirtyList!=null && thirtyList.size()>0){
            String msg = DateUtil.format(DateUtil.offsetDay(new Date(), 30),"yyyy年MM月dd日");
            for (OrganizeDTO organizeDTO : thirtyList){
                if (profileActive.equals("dev") || profileActive.equals("test")){
                    if (phone.contains(organizeDTO.getPhone())){
                        List<Object> thirtyPhoneList = new ArrayList<>();
                        jsonArray = new JSONArray();
                        thirtyPhoneList.add(organizeDTO.getPhone());
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("name",organizeDTO.getFullNname());
                        jsonObject.put("msg",msg);
                        jsonArray.add(jsonObject);

                        thirtyPhoneList.add(serviceNumber);
                        JSONObject jsonObjectService = new JSONObject();
                        jsonObjectService.put("name",organizeDTO.getFullNname());
                        jsonObjectService.put("msg",msg);
                        jsonArray.add(jsonObjectService);
                        String bizId = aliSmsMessage.sendMsg(thirtyPhoneList,jsonArray.toJSONString(),templateCode);
                        if (bizId!=null && !"".equals(bizId)) {
                            List<QuerySendDetailsResponse.SmsSendDetailDTO> responseList = aliSmsMessage.messageSendState(thirtyPhoneList, bizId);
                            insertMysql(responseList,bizId);
                        }
                    }
                }
            }

        }
    }

    public static void main(String[] args) {
        DateTime thirtyDateTime = DateUtil.offset(new Date(), DateField.DAY_OF_YEAR, 30);
        Long serviceThirtyBegin = DateUtil.beginOfDay(thirtyDateTime).getTime()/1000;
        Long serviceThirtyEnd = DateUtil.endOfDay(thirtyDateTime).getTime()/1000;
        System.out.println(serviceThirtyBegin+","+serviceThirtyEnd);
    }
}
