package com.imooc.miaosha.service;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.redis.MiaoshaKey;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MiaoshaService {

    private final static Logger logger = LoggerFactory.getLogger(MiaoshaService.class);

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisService redisService;

    //扣减库存,创建订单
    @Transactional(rollbackFor = Exception.class)
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        logger.info(String.format("run method miaosha param=%s", goods.toString()));
        Boolean flag = goodsService.reduceStock(goods);
        if (flag) {
            return orderService.createOrder(user, goods);
        } else {
            //失败,将redis中的商品设置为true
            setGoodsOver(goods.getId());
            return null;
        }
    }

    //设置秒杀商品状态
    public void setGoodsOver(long gooodsId) {
        redisService.set(MiaoshaKey.gooodsOver, "" + gooodsId, true);
    }

    //获取秒杀商品状态
    public boolean getGoodsOver(long goodsId) {
        return redisService.exists(MiaoshaKey.gooodsOver, "" + goodsId);
    }

}
