//package com.ycandyz.master.controller.mall;
//
//import com.ycandyz.master.api.ReturnResponse;
//import com.ycandyz.master.kafka.KafkaConsumer;
//import com.ycandyz.master.kafka.KafkaProducer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Set;
//
//@RestController
//public class KafkaController {
//
//    @Autowired
//    private KafkaProducer kafkaProducer;
//
//    @Autowired
//    private KafkaConsumer kafkaConsumer;
//
//    /**
//     * 创建topic
//     * @return
//     * @throws InterruptedException
//     */
//    @GetMapping("/kafka/topic/create")
//    public ReturnResponse<String> createTopic() throws InterruptedException {
//        kafkaProducer.testCreateTopic("topic_kafka_test");
//        return ReturnResponse.success("成功");
//    }
//
//    /**
//     * 获取topic集合
//     * @return
//     * @throws Exception
//     */
//    @GetMapping("/kafka/topic/list")
//    public ReturnResponse<Set<String>> getTopicList() throws Exception {
//        return ReturnResponse.success(kafkaProducer.getAllTopic());
//    }
//
//    /**
//     * kafka producer发送消息
//     * @return
//     */
//    @GetMapping("/kafka/producer/send")
//    public ReturnResponse<String> sendMsg(){
//        kafkaProducer.send("你好","topic_kafka_test");
//        return ReturnResponse.success("发送成功");
//    }
//
//}
