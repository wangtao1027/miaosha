package com.imooc.miaosha.rabbitmq;

import org.springframework.context.annotation.Configuration;

/**
 * @author wt
 * @version 1.0
 * @date 2020/9/4 8:51
 */
@Configuration
public class MQConfig {

    public static final String MIAOSHA_QUEUE = "quere";
    public static final String queue = "queue";
    public static final String TOPIC_QUEUE1 = "topic_queue1";
    public static final String TOPIC_QUEUE2 = "topic_queue2";
    public static final String FANT_OUT1 = "fan_out1";
    public static final String FANT_OUT2 = "fan_out2";
    public static final String TOPIC1 = "topic1";
    public static final String TOPIC2 = "topic2";

}
