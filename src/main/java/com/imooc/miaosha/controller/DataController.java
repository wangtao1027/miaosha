package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.MessageParam;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wt
 * @version 1.0
 * @date 2020/9/11 16:12
 */
@Controller
@RequestMapping("/dataGet")
public class DataController {

    private final static Logger logger = LoggerFactory.getLogger(DataController.class);

    @RequestMapping("/getMessage")
    @ResponseBody
    public Result<String> getMessage(MessageParam messageParam) {
        logger.error(String.format("run method params=%s", messageParam.toString()));
        if (messageParam == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        return Result.success(messageParam.toString());
    }

}
