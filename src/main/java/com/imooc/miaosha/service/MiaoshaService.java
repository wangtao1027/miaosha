package com.imooc.miaosha.service;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MiaoshaService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    //扣减库存,创建订单
    @Transactional(rollbackFor = Exception.class)
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        goodsService.reduceStock(goods);
        return orderService.createOrder(user, goods);
    }

}
