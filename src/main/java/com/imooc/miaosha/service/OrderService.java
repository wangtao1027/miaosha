package com.imooc.miaosha.service;

import com.imooc.miaosha.dao.OrderDao;
import com.imooc.miaosha.domain.MiaoShaOrder;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class OrderService {

    private final static Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Resource
    private OrderDao orderDao;

    public MiaoShaOrder getMiaoshaOrderByUserIdGoodsId(Long userId, Long goodsId) {
        return orderDao.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
    }

    public OrderInfo getOrderById(long orderId){
        return orderDao.getOrderById(orderId);
    }

   //穿件订单,创建订单表,穿件订单关联表
    @Transactional(rollbackFor = Exception.class)
    public OrderInfo createOrder(MiaoshaUser user, GoodsVo goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(user.getId());
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setCreateDate(new Date());
        long orderId = orderDao.insert(orderInfo);  //返回插入表中数据的主键id
        MiaoShaOrder miaoShaOrder = new MiaoShaOrder();
        miaoShaOrder.setUserId(user.getId());
        miaoShaOrder.setOrderId(orderId);
        miaoShaOrder.setGoodsId(goods.getId());
        orderDao.insertMiaoshaOrder(miaoShaOrder);
        return orderInfo;
    }
}
