package com.imooc.miaosha.message.service;

import com.imooc.miaosha.message.controller.MessageController;
import com.imooc.miaosha.redis.CheckCodeKey;
import com.imooc.miaosha.redis.RedisService;
import groovyjarjarantlr.SemanticException;
import org.apache.commons.lang3.RandomStringUtils;
import org.omg.CORBA.DynAnyPackage.Invalid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author wt
 * @version 1.0
 * @date 2021/3/16 14:00
 */
@Service
public class MessageService {

    private final static Logger logger = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisService redisService;

    public void sendSms(String mobile) {
        logger.info(String.format("run method sendSms params = %s", mobile));
        String checkCode = RandomStringUtils.random(6, false, true);

        redisService.set(CheckCodeKey.getCheckCode, mobile, checkCode);
        HashMap<String, String> messageParams = new HashMap<String, String>();
        messageParams.put("mobile", mobile);
        messageParams.put("checkCode", checkCode);
        rabbitTemplate.convertAndSend("sms", messageParams);
    }

}
