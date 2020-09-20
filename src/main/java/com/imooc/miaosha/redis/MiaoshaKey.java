package com.imooc.miaosha.redis;

/**
 * @author wt
 * @version 1.0
 * @date 2020/9/10 15:05
 */
public class MiaoshaKey extends BasePrefix {

    public MiaoshaKey(int expireSeconds, String prefix) {
        super(expireSeconds,prefix);
    }

    public static MiaoshaKey gooodsOver = new MiaoshaKey(0, "go");
    public static MiaoshaKey miaoshaPath = new MiaoshaKey(60, "mp");
}
