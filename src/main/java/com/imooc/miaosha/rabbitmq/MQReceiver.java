package com.imooc.miaosha.rabbitmq;

import com.imooc.miaosha.domain.MiaoShaOrder;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.service.GoodsService;
import com.imooc.miaosha.service.MiaoshaService;
import com.imooc.miaosha.service.OrderService;
import com.imooc.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQReceiver {

    private static Logger logger = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RedisService redisService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;

    /**
     * 秒杀接口优化,消息接受者
     *
     * @param message
     */
    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
    public void receiveMiaosha(String message) {
        logger.info(String.format("run method receiveMiaosha params= %s", message));
        MiaoshaMessage miaoshaMessage = redisService.stringToBean(message, MiaoshaMessage.class);
        long goodsId = miaoshaMessage.getGoodsId();
        MiaoshaUser user = miaoshaMessage.getUser();

        //获取秒杀商品
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        //判断库存是否为0
        Integer stock = goods.getStockCount();
        if (stock < 0) {
            return;
        }

        //判断是否秒杀到了
        MiaoShaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return;
        }

        //创建订单
        miaoshaService.miaosha(user,goods);
    }

    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive(String message) {
        logger.error("receive收到的信息是:" + message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message) {
        logger.error("receiveTopic1收到的信息是:" + message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message) {
        logger.error("receiveTopic2收到的信息是:" + message);
    }

    @RabbitListener(queues = MQConfig.HEADER_QUEUE)
    public void receiveHeader(byte[] message) {
        logger.error("receiveHeader:" + new String(message));
    }
}
