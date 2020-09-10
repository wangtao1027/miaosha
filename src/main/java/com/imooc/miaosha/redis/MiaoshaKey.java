package com.imooc.miaosha.redis;

/**
 * @author wt
 * @version 1.0
 * @date 2020/9/10 15:05
 */
public class MiaoshaKey extends BasePrefix {

    public MiaoshaKey(String prefix) {
        super(prefix);
    }

    public static MiaoshaKey gooodsOver = new MiaoshaKey("go");

}
