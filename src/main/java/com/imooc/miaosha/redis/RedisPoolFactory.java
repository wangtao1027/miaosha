package com.imooc.miaosha.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 读取配置文件中的配置属性，创建Jedis连接池
 */
@Service
public class RedisPoolFactory {

    @Autowired
    RedisConfig redisConfig;

    @Bean
    public JedisPool JedisPoolFactory() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
        poolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        poolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait() * 1000);  //查看源码，方法是要设置的是毫秒值
        JedisPool jp = new JedisPool(
                poolConfig,
                redisConfig.getHost(),
                redisConfig.getPort(),
                redisConfig.getTimeout() * 1000,
                redisConfig.getPassword(),
                0
        );
        return jp;
    }

}
