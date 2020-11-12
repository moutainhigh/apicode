//package com.ycandyz.master.service.quartz;
//
//import cn.hutool.core.date.DateField;
//import cn.hutool.core.date.DateTime;
//import cn.hutool.core.date.DateUtil;
//import com.ycandyz.master.aliyun.sms.AliSmsMessage;
//import com.ycandyz.master.dao.organize.OrganizeDao;
//import com.ycandyz.master.dto.organize.OrganizeDTO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Configuration
//@EnableScheduling
//public class SMSJob {
//
//    @Value("${aliyun.sms.service-number}")
//    private String serviceNumber;
//    //对应模板代码
//    @Value("${aliyun.sms.template_code}")
//    private String templateCode;
//
//    @Autowired
//    private OrganizeDao organizeDao;
//
//    @Autowired
//    private AliSmsMessage aliSmsMessage;
//
//    /**
//     * u客用户发送短信，到期提醒
//     */
//    @Scheduled(cron = "0 0 8 * * ?")
//    private void configureTasks() {
//        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
//        //当天
//        Long serviceNowBegin = DateUtil.beginOfDay(new Date()).getTime()/1000;
//        Long serviceNowEnd = DateUtil.endOfDay(new Date()).getTime()/1000;
//        List<OrganizeDTO> list = organizeDao.queryByServiceTime(serviceNowBegin,serviceNowEnd);
//        if (list!=null && list.size()>0){
//            List<String> phoneList = list.stream().map(OrganizeDTO::getPhone).collect(Collectors.toList());
//            phoneList.add(serviceNumber);
//            aliSmsMessage.sendMsg(phoneList,null,templateCode);
//        }
//
//        //七天
//        DateTime sevenDateTime = DateUtil.offset(new Date(), DateField.DAY_OF_YEAR, 7);
//        Long serviceSevenBegin = DateUtil.beginOfDay(sevenDateTime).getTime()/1000;
//        Long serviceSevenEnd = DateUtil.endOfDay(sevenDateTime).getTime()/1000;
//        List<OrganizeDTO> sevenList = organizeDao.queryByServiceTime(serviceSevenBegin,serviceSevenEnd);
//        if (sevenList!=null && sevenList.size()>0){
//            List<String> sevenPhoneList = sevenList.stream().map(OrganizeDTO::getPhone).collect(Collectors.toList());
//            sevenPhoneList.add(serviceNumber);
//            aliSmsMessage.sendMsg(sevenPhoneList,null,templateCode);
//        }
//
//        //30天
//        DateTime thirtyDateTime = DateUtil.offset(new Date(), DateField.DAY_OF_YEAR, 30);
//        Long serviceThirtyBegin = DateUtil.beginOfDay(thirtyDateTime).getTime()/1000;
//        Long serviceThirtyEnd = DateUtil.endOfDay(thirtyDateTime).getTime()/1000;
//        List<OrganizeDTO> thirtyList = organizeDao.queryByServiceTime(serviceThirtyBegin,serviceThirtyEnd);
//        if (thirtyList!=null && thirtyList.size()>0){
//            List<String> thirtyPhoneList = thirtyList.stream().map(OrganizeDTO::getPhone).collect(Collectors.toList());
//            thirtyPhoneList.add(serviceNumber);
//            aliSmsMessage.sendMsg(thirtyPhoneList,null,templateCode);
//        }
//    }
//
//}
