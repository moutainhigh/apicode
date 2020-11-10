package com.ycandyz.master.kafka;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;


/**
 * kafka生产者
 *
 */
@Component
@Slf4j
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired // adminClien需要自己生成配置bean
    private AdminClient adminClient;

    public void send(Object obj, String topicId) {
        Message message = new Message();
        message.setId(topicId+"#"+UUID.randomUUID().toString());
        message.setTime(System.currentTimeMillis());
        message.setMsg(obj);
        String obj2String = JSONObject.toJSONString(message);
        log.info("准备发送消息为：{}", obj2String);
        //发送消息
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicId, obj2String);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                //发送失败的处理
                log.info(topicId + " - 生产者 发送消息失败：" + throwable.getMessage());
            }
            @Override
            public void onSuccess(SendResult<String, String> stringObjectSendResult) {
                //成功的处理
                log.info(topicId + " - 生产者 发送消息成功：" + stringObjectSendResult.toString());
            }
        });
    }

    /**
     * 新增分片
     * @param topicName
     * @throws InterruptedException
     */
    public void testCreateTopic(String topicName) throws InterruptedException {
        // 这种是手动创建 //10个分区，一个副本
        // 分区多的好处是能快速的处理并发量，但是也要根据机器的配置
        NewTopic topic = new NewTopic(topicName, 10, (short) 1);
        adminClient.createTopics(Arrays.asList(topic));
        Thread.sleep(1000);
    }

    /**
     * 获取所有分片
     * @throws Exception
     */
    public Set<String> getAllTopic() throws Exception {
        ListTopicsResult listTopics = adminClient.listTopics();
        Set<String> topics = listTopics.names().get();
        for (String topic : topics) {
            System.err.println(topic);
        }
        return topics;
    }
}
