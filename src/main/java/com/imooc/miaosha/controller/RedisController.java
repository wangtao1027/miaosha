package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/redis")
public class RedisController {

    private final static Logger logger = LoggerFactory.getLogger(RedisController.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/add")
    public void add(User user) {
        logger.info("class: DemoRedisController Params:" + user.toString());
        int id = user.getId();
        String name = user.getName();

//        redisTemplate.boundValueOps("id").set(id);
//        redisTemplate.boundValueOps("name").set(name);
        redisTemplate.opsForValue().set("id",id);
        redisTemplate.opsForValue().set("name",name);
    }


}
