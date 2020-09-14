package com.imooc.miaosha.service;

import com.imooc.miaosha.dao.OrderDao;
import com.imooc.miaosha.domain.MiaoShaOrder;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.redis.OrderKey;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class OrderService {

    private final static Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Resource
    private OrderDao orderDao;

    @Autowired
    private RedisService redisService;

    public MiaoShaOrder getMiaoshaOrderByUserIdGoodsId(Long userId, Long goodsId) {
        //从redis缓存中读取
//        return orderDao.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
        return redisService.get(OrderKey.getMiaoshaOrderByUidGid, String.valueOf(userId + "_" + goodsId), MiaoShaOrder.class);
    }

    public OrderInfo getOrderById(long orderId) {
        return orderDao.getOrderById(orderId);
    }

    //创建订单,创建订单表,创建订单关联表
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
//        long orderId = orderDao.insert(orderInfo);  //返回插入表中数据的主键id
        orderDao.insert(orderInfo);
        MiaoShaOrder miaoShaOrder = new MiaoShaOrder();
        miaoShaOrder.setUserId(user.getId());
        miaoShaOrder.setOrderId(orderInfo.getId()); //这里为什么可以这样写?
        miaoShaOrder.setGoodsId(goods.getId());
        orderDao.insertMiaoshaOrder(miaoShaOrder);

        //存入到缓存当中
        redisService.set(OrderKey.getMiaoshaOrderByUidGid, "" + user.getId() + "_" + goods.getId(), miaoShaOrder);

        return orderInfo;
    }
}
