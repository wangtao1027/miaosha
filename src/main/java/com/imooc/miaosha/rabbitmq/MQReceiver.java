package com.imooc.miaosha.rabbitmq;

import com.imooc.miaosha.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQReceiver {

    private static Logger logger = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    AmqpTemplate amqpTemplate;

    @Autowired
    RedisService redisService;

    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive(String message) {
        logger.error("receive收到的信息是:" + message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message) {
        logger.error("receiveTopic1收到的信息是:" + message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message) {
        logger.error("receiveTopic2收到的信息是:" + message);
    }

// fantout迷行两个交换机都能显示对应的消息
//    @RabbitListener(queues = MQConfig.FANOUT_EXCHANGE)
//    public void receiveFanout(String message) {
//        logger.error("receiveFanout收到的信息是:" + message);
//    }
}
