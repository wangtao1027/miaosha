package com.imooc.miaosha.message.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author wt
 * @version 1.0
 * @date 2021/3/16 14:05
 */
@Component
public class SmsListener {

    @RabbitListener(queues = "sms")
    public void executeSms(Map<String, String> map) {
        String mobile = map.get("mobile");
        String checkCode = map.get("checkCode");

        System.out.println("手机号:" + mobile + "_" + "短信验证码:" + checkCode);

        //将生成的短信验证码,通过阿里云服务进行操作,然后发送给用户
        //阿里云短信服务接入接口
    }

}
