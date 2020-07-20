package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/redis")
public class RedisController {

    private final static Logger logger = LoggerFactory.getLogger(RedisController.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @RequestMapping("/add")
    public void add(User user) {
        logger.info(String.format("run method add params = %s", user.toString()));
        String id = user.getId() + "";
        String name = user.getName();
        redisTemplate.opsForValue().set("id", id);
        redisTemplate.opsForValue().set("name", name);
    }

    @RequestMapping("/get")
    @ResponseBody
    public User get(String key1, String key2) {
        logger.info(String.format("run method get params = %s,%s", key1, key2));
        String idStr = redisTemplate.opsForValue().get(key1);
        String nameStr = redisTemplate.opsForValue().get(key2);
        User user = new User();
        user.setId(Integer.valueOf(idStr));
        user.setName(nameStr);
        return user;
    }

}
