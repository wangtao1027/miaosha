package com.imooc.miaosha.redis;

/**
 * @author wt
 * @version 1.0
 * @date 2021/3/16 14:42
 */
public class CheckCodeKey extends BasePrefix {

    public CheckCodeKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static CheckCodeKey getCheckCode = new CheckCodeKey(300,"ck");    //验证码过期时间设置为五分钟

}
