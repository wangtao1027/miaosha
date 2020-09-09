package com.imooc.miaosha.redis;

public class GoodsKey extends BasePrefix {

    public GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 获取详情,保存时间60秒
     */
    public static GoodsKey getGoodsList = new GoodsKey(60,"gl");
    public static GoodsKey getGoodsDetail = new GoodsKey(60,"gd");

    /**
     * 秒杀商品数量进行缓存预热
     */
    public static GoodsKey getMiaoshaGoodsStock = new GoodsKey(0,"gs");

}
