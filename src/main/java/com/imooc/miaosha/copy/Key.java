package com.imooc.miaosha.copy;

public interface Key {

    public int expirationTime();    //设置数据过期时间

    public String getKeyPrefix();   //获取Redis中key的前缀名

}