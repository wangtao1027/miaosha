package com.imooc.miaosha.service;

import com.imooc.miaosha.domain.MiaoShaOrder;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.redis.MiaoshaKey;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.util.MD5Util;
import com.imooc.miaosha.util.UUIDUtil;
import com.imooc.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

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

    /**
     * 获取秒杀结果
     *
     * @param userId
     * @param goodsId
     * @return
     */
    public long getMiaoshaResult(long userId, long goodsId) {
        //缓存中获取是否获取到了秒杀商品
        MiaoShaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
        if (order != null) {
            return order.getOrderId();
        } else {
            boolean goodsOver = getGoodsOver(goodsId);
            if (goodsOver) {
                return -1;  //秒杀失败
            } else {
                return 0;   //继续轮询
            }
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

    @Transactional(rollbackFor = Exception.class)
    public void reset(List<GoodsVo> goodsVoList) {
        //重置数据库库存
        goodsService.resetStock(goodsVoList);
        //删除订单
        orderService.deleteOrder();
    }

    //获取秒杀地址匹配结果
    public boolean checkPath(MiaoshaUser user, Long goodsId, String path) {
        if (user == null || path == null) {
            return false;
        }
        String pathStr = redisService.get(MiaoshaKey.miaoshaPath, user.getId() + "_" + goodsId, String.class);
        return path.equals(pathStr);
    }

    //创建秒杀地址
    public String createMiaoshaPath(MiaoshaUser user, long goodsId) {
        if (user == null || goodsId <= 0) {
            return null;
        }
        String path = MD5Util.md5(UUIDUtil.uuid() + "123456");
        redisService.set(MiaoshaKey.miaoshaPath, "" + user.getId() + "_" + goodsId, path);
        return path;
    }

    //创建秒杀验证码
    public BufferedImage createMiaoshaVerifyCode(MiaoshaUser user, long goodsId) {
        if (user == null || goodsId <= 0) {
            return null;
        }
        int width = 80;
        int height = 32;
        //create the image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // set the background color
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width, height);
        // draw the border
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);
        // create a random instance to generate the codes
        Random rdm = new Random();
        // make some confusion
        for (int i = 0; i < 50; i++) {
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }
        // generate a random code
        String verifyCode = generateVerifyCode(rdm);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 24));
        g.drawString(verifyCode, 8, 24);
        g.dispose();
        //把验证码存到redis中
        int rnd = calc(verifyCode);
        redisService.set(MiaoshaKey.getMiaoshaVerifyCode, "" + user.getId() + "_" + goodsId, rnd);
        return image;
    }

    private static int calc(String exp) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (Integer) engine.eval(exp);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static char[] ops = new char[]{'+', '-', '*'};

    /**
     * + - *
     */
    private String generateVerifyCode(Random rdm) {
        int num1 = rdm.nextInt(10);
        int num2 = rdm.nextInt(10);
        int num3 = rdm.nextInt(10);
        char op1 = ops[rdm.nextInt(3)];
        char op2 = ops[rdm.nextInt(3)];
        String exp = "" + num1 + op1 + num2 + op2 + num3;
        return exp;
    }

    //校验验证码是否正确
    public boolean checkVerifyCode(MiaoshaUser user, long goodsId, int verifyCode) {
        if (user == null || goodsId <= 0) {
            return false;
        }
        Integer code = redisService.get(MiaoshaKey.getMiaoshaVerifyCode, "" + user.getId() + "_" + goodsId, Integer.class);
        return verifyCode == code;
    }
}
