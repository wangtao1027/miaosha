package com.imooc.miaosha.redis;

/**
 * @author wt
 * @version 1.0
 * @date 2020/9/23 9:06
 */
public class AccessKey extends BasePrefix {

    public AccessKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static AccessKey access = new AccessKey(5,"ac");     //5秒内次数超过5次,提示访问太频繁

}
