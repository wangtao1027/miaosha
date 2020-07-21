package com.imooc.miaosha.copy;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisServiceOne {

    private final static Logger logger = LoggerFactory.getLogger(RedisServiceOne.class);

    @Autowired
    JedisPool jedisPool;

    /**
     * 获取单个值
     * @param prefix
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(Key prefix, String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //获取redis中保存的key
            String realKey = prefix.getKeyPrefix() + key;
            String s = jedis.get(realKey);
            T t = stringToBean(s, clazz);
            return t;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 设置单个对象
     * @param prefix
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> boolean set(Key prefix,String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String valueStr = beanToString(value);
            if (valueStr == null || valueStr.length() < 0) {
                return false;
            }
            String realKey = prefix.getKeyPrefix() + key;
            logger.error(realKey);
            int second = prefix.expirationTime();
            if (second <= 0) {
                //数据持久化
                jedis.set(realKey,valueStr);
            } else {
                jedis.setex(realKey,second,valueStr);
            }
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 泛型类型转String
     *
     * @param value
     * @param <T>
     * @return
     */
    private <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return String.valueOf(value);
        } else if (clazz == String.class) {
            return (String) value;
        } else if (clazz == Long.class || clazz == long.class) {
            return String.valueOf(value);
        } else {
            return JSON.toJSONString(value);
        }

    }

    /**
     * String类型转换为bean类型
     *
     * @param str
     * @param clazz 指定需要的一个泛型类型
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")      //编译器忽略指定的警告，不用在编译完成后出现警告信息。
    private <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() < 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else if (clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    private void returnToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

}
