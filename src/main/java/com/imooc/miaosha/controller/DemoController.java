package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.User;
import com.imooc.miaosha.rabbitmq.MQSender;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.redis.UserKey;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo")
public class DemoController {

    private final static Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MQSender mqSender;

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    //1.rest api json输出 2.页面
    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> hello() {
        return Result.success("hello,imooc");
        // return new Result(0, "success", "hello,imooc");
    }

    @RequestMapping("/helloError")
    @ResponseBody
    public Result<String> helloError() {
        return Result.error(CodeMsg.SERVER_ERROR);
        //return new Result(500102, "XXX");
    }

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model) {
        model.addAttribute("name", "Joshua");
        return "hello";
    }

    @RequestMapping("/getUser")
    @ResponseBody
    public User getUser(int id) {
        return userService.getUser(id);
    }

    @RequestMapping("/getUser1")
    @ResponseBody
    public Result<User> getUser1(int id) {
        User user = userService.getUser(id);
        return Result.success(user);
    }

    @RequestMapping("/tx")
    @ResponseBody
    public void demoTx() {
        User user = new User(1, "zhangsan");
        userService.add(user);
    }

    @RequestMapping("/addTx")
    @ResponseBody
    public boolean addTx() {
        return userService.addTx();
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet() {
        //UserKey:id1
        User user = redisService.get(UserKey.getById, "" + 1, User.class);
        logger.info(String.format("run method redisGet resultParams = %s", user.toString()));
        return Result.success(user);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet() {
        User user = new User();
        user.setId(1);
        user.setName("1111");
        redisService.set(UserKey.getById, "" + 1, user);
        return Result.success(true);
    }

    //测试RabbitMQ
    @RequestMapping("/mq")
    @ResponseBody
    public Result<String> mq() {
        mqSender.send("hello hanxiang");
        return Result.success("请求成功!");
    }

    @RequestMapping("/mq/topic")
    @ResponseBody
    public Result<String> topic() {
        mqSender.sendTopic("hello hanxiang");
        return Result.success("请求成功!");
    }

    @RequestMapping("/mq/fanout")
    @ResponseBody
    public Result<String> fanout() {
        mqSender.sendFanout("hello hanxiang");
        return Result.success("请求成功!");
    }

    @RequestMapping("/mq/header")
    @ResponseBody
    public Result<String> header() {
        mqSender.sendHeader("hello  hanxiang");
        return Result.success("请求成功!");
    }

}
