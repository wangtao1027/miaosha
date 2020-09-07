package com.imooc.miaosha.service;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
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

    //扣减库存,创建订单
    @Transactional(rollbackFor = Exception.class)
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        logger.info(String.format("run method miaosha param=%s",goods.toString()));
        goodsService.reduceStock(goods);
        return orderService.createOrder(user, goods);
    }

}
