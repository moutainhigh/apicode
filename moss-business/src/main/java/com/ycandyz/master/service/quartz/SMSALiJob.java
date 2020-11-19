package com.ycandyz.master.service.quartz;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.ycandyz.master.aliyun.sms.AliSmsMessage;
import com.ycandyz.master.constant.KafkaConstant;
import com.ycandyz.master.dao.organize.OrganizeDao;
import com.ycandyz.master.dao.sms.SmsSendQueueLogDao;
import com.ycandyz.master.dto.organize.OrganizeDTO;
import com.ycandyz.master.entities.sms.SmsSendQueueLog;
import com.ycandyz.master.kafka.KafkaProducer;
import com.ycandyz.master.kafka.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
@EnableScheduling
public class SMSALiJob {

    @Value("${aliyun.sms.service-number}")
    private String serviceNumber;
    //对应模板代码
    @Value("${aliyun.sms.template_code}")
    private String templateCode;

    @Value("${topic.sms}")
    private String topicId;

    @Autowired
    private OrganizeDao organizeDao;

    @Autowired
    private AliSmsMessage aliSmsMessage;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private SmsSendQueueLogDao smsSendQueueLogDao;

    /**
     * u客用户发送短信，到期提醒
     */
    @Scheduled(cron = "0 0 8 * * ?")
    private void configureTasks() {
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
        JSONArray jsonArray = null;
        //当天
        Long serviceNowBegin = DateUtil.beginOfDay(new Date()).getTime()/1000;
        Long serviceNowEnd = DateUtil.endOfDay(new Date()).getTime()/1000;
        List<OrganizeDTO> list = organizeDao.queryByServiceTime(serviceNowBegin,serviceNowEnd);
        if (list!=null && list.size()>0){
            jsonArray = new JSONArray();
            for (OrganizeDTO organizeDTO : list){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name",organizeDTO.getFullNname());
                jsonObject.put("msg","今日");
                jsonArray.add(jsonObject);
            }
            List<String> phoneList = list.stream().map(OrganizeDTO::getPhone).collect(Collectors.toList());
            phoneList.add(serviceNumber);
            String bizId = aliSmsMessage.sendMsg(phoneList,jsonArray.toJSONString(),templateCode);
            if (bizId!=null && !"".equals(bizId)) {
                List<QuerySendDetailsResponse.SmsSendDetailDTO> responseList = aliSmsMessage.messageSendState(phoneList, bizId);
                insertMysql(responseList,bizId);
            }
        }

        //七天
        DateTime sevenDateTime = DateUtil.offset(new Date(), DateField.DAY_OF_YEAR, 7);
        Long serviceSevenBegin = DateUtil.beginOfDay(sevenDateTime).getTime()/1000;
        Long serviceSevenEnd = DateUtil.endOfDay(sevenDateTime).getTime()/1000;
        List<OrganizeDTO> sevenList = organizeDao.queryByServiceTime(serviceSevenBegin,serviceSevenEnd);
        if (sevenList!=null && sevenList.size()>0){
            jsonArray = new JSONArray();
            String msg = DateUtil.format(DateUtil.offsetDay(new Date(), 7),"yyyy年MM月dd日");
            for (OrganizeDTO organizeDTO : sevenList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name",organizeDTO.getFullNname());
                jsonObject.put("msg",msg);
                jsonArray.add(jsonObject);
            }
            List<String> sevenPhoneList = sevenList.stream().map(OrganizeDTO::getPhone).collect(Collectors.toList());
            sevenPhoneList.add(serviceNumber);
            String bizId = aliSmsMessage.sendMsg(sevenPhoneList,jsonArray.toJSONString(),templateCode);
            if (bizId!=null && !"".equals(bizId)) {
                List<QuerySendDetailsResponse.SmsSendDetailDTO> responseList = aliSmsMessage.messageSendState(sevenPhoneList, bizId);
                insertMysql(responseList,bizId);
            }
        }

        //30天
        DateTime thirtyDateTime = DateUtil.offset(new Date(), DateField.DAY_OF_YEAR, 30);
        Long serviceThirtyBegin = DateUtil.beginOfDay(thirtyDateTime).getTime()/1000;
        Long serviceThirtyEnd = DateUtil.endOfDay(thirtyDateTime).getTime()/1000;
        List<OrganizeDTO> thirtyList = organizeDao.queryByServiceTime(serviceThirtyBegin,serviceThirtyEnd);
        if (thirtyList!=null && thirtyList.size()>0){
            jsonArray = new JSONArray();
            String msg = DateUtil.format(DateUtil.offsetDay(new Date(), 30),"yyyy年MM月dd日");
            for (OrganizeDTO organizeDTO : sevenList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name",organizeDTO.getFullNname());
                jsonObject.put("msg",msg);
                jsonArray.add(jsonObject);
            }
            List<String> thirtyPhoneList = thirtyList.stream().map(OrganizeDTO::getPhone).collect(Collectors.toList());
            thirtyPhoneList.add(serviceNumber);
            String bizId = aliSmsMessage.sendMsg(thirtyPhoneList,jsonArray.toJSONString(),templateCode);
            if (bizId!=null && !"".equals(bizId)) {
                List<QuerySendDetailsResponse.SmsSendDetailDTO> responseList = aliSmsMessage.messageSendState(thirtyPhoneList, bizId);
                insertMysql(responseList,bizId);
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
                    message.setId(topicId+"#"+UUID.randomUUID().toString());
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
}
