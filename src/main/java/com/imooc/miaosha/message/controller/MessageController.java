package com.imooc.miaosha.message.controller;

import com.imooc.miaosha.message.service.MessageService;
import com.imooc.miaosha.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wt
 * @version 1.0
 * @date 2021/3/16 13:57
 */
@Controller
@RequestMapping("/message")
public class MessageController {

    private final static Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    @RequestMapping("/sendMessage")
    @ResponseBody
    public Result<String> sendMessage(@RequestParam("mobile") String mobile) {
        logger.info("电话号码:" + mobile);
        messageService.sendSms(mobile);
        return Result.success("请求成功!");
    }

}
