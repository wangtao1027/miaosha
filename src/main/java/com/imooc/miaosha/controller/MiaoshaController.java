package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.MiaoShaOrder;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.rabbitmq.MQSender;
import com.imooc.miaosha.rabbitmq.MiaoshaMessage;
import com.imooc.miaosha.redis.GoodsKey;
import com.imooc.miaosha.redis.MiaoshaKey;
import com.imooc.miaosha.redis.OrderKey;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.GoodsService;
import com.imooc.miaosha.service.MiaoshaService;
import com.imooc.miaosha.service.OrderService;
import com.imooc.miaosha.util.MD5Util;
import com.imooc.miaosha.util.UUIDUtil;
import com.imooc.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {        //实现这个类,重写方法,会在容器加载时调用实现方法

    private final static Logger logger = LoggerFactory.getLogger(MiaoshaController.class);

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MQSender mqSender;

    /**
     * 内存标记
     */
    public Map<Long, Boolean> localMap = new HashMap<Long, Boolean>();      //值为true代表秒杀商品已经结束

    /**
     * 系统初始化执行方法
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        if (goodsList == null) {
            return;
        }
        for (GoodsVo goodsVo : goodsList) {
            redisService.set(GoodsKey.getMiaoshaGoodsStock, "" + goodsVo.getId(), goodsVo.getStockCount());
            localMap.put(goodsVo.getId(), false);
        }
    }

    /**
     * 执行秒杀方法
     *
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping("/{path}/do_miaosha")
    @ResponseBody
    public Result<Integer> miaosha(Model model, MiaoshaUser user, @RequestParam("goodsId") Long goodsId, @PathVariable("path") String path) {
        logger.info(String.format("run method miaosha param=%s", goodsId));
        model.addAttribute("user", user);
        //判断用户是否登录
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        //校验秒杀地址
        boolean check = miaoshaService.checkPath(user, goodsId, path);
        if (!check) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }

        //内存标记,减少redis访问
        Boolean over = localMap.get(goodsId);
        if (over) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }

        //预减库存
        Long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock, "" + goodsId);
        //判断是否还有库存
        if (stock < 0) {
            localMap.put(goodsId, true); //商品状态设置为结束
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }

        //判断是否已经秒杀到了
        MiaoShaOrder miaoshsOrder = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (miaoshsOrder != null) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }

        //入队
        MiaoshaMessage mm = new MiaoshaMessage();
        mm.setGoodsId(goodsId);
        mm.setUser(user);
        //执行成功,程序继续,执行失败程序直接终端,前端收不到消息返回码
        mqSender.sendMiaosha(mm);
        return Result.success(0);       //排队中

//        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
//        int stock = goods.getStockCount();
//        //判断是否还有库存
//        if (stock <= 0) {
//            return Result.error(CodeMsg.MIAO_SHA_OVER);
//        }
//
//        //判断是否已经秒杀到了
//        MiaoShaOrder miaoshsOrder = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
//        if (miaoshsOrder != null) {
//            return Result.error(CodeMsg.REPEATE_MIAOSHA);
//        }
//        //扣减库存,创建订单 写入秒杀订单
//        OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
//        return Result.success(orderInfo);

    }

    /**
     * 获取秒杀结果
     * goodsId : 成功
     * -1 : 秒杀失败
     * 0 : 继续轮询
     *
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping("/result")
    @ResponseBody
    public Result<Long> result(Model model, MiaoshaUser user, @RequestParam("goodsId") Long goodsId) {
        logger.info(String.format("run method result param=%s", goodsId));
        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long result = miaoshaService.getMiaoshaResult(user.getId(), goodsId);
        return Result.success(result);
    }

    /**
     * 重置缓存中和数据库中的订单
     *
     * @param model
     */
    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    @ResponseBody
    public Result<Boolean> reset(Model model) {
        logger.info("run method reset");
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        for (GoodsVo goodsVo : goodsList) {
            goodsVo.setStockCount(10);
            //初始化库存数量
//            redisService.set(MiaoshaKey.gooodsOver, "" + goodsVo.getId(), goodsVo);
            redisService.set(GoodsKey.getMiaoshaGoodsStock, "" + goodsVo.getId(), 10);
            //商品秒杀状态设置为未结束
            localMap.put(goodsVo.getId(), false);
        }

        //删除缓存中的订单数据
        redisService.delete(OrderKey.getMiaoshaOrderByUidGid);
        redisService.delete(MiaoshaKey.gooodsOver);

        miaoshaService.reset(goodsList);
        return Result.success(true);
    }

    /**
     * 获取秒杀地址
     *
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(value = "/path", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> getMiaoshaPath(Model model, MiaoshaUser user, @RequestParam("goodsId") long goodsId, @RequestParam("verifyCode") int verifyCode) {
        logger.info("run method getMiaoshaPath");
        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        //验证验证码是否正确
        boolean verifyResult = miaoshaService.checkVerifyCode(user, goodsId, verifyCode);
        if (!verifyResult) {
            return Result.error(CodeMsg.ERROR_VERIFICATION_CODE);
        }

        String path = miaoshaService.createMiaoshaPath(user, goodsId);
        return Result.success(path);
    }

    /**
     * 获取执行秒杀业务验证码
     *
     * @param response
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/verifyCode", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> getMiaoshaVerifyCode(HttpServletResponse response, MiaoshaUser user, @RequestParam("goodsId") long goodsId) {
        logger.info(String.format("run method getMiaoshaVerifyCode param=%s", goodsId));
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //生成验证码逻辑
        try {
            BufferedImage image = miaoshaService.createMiaoshaVerifyCode(user, goodsId);
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "JPEG", out);
            out.flush();
            out.close();
            return null;    //这里返回为空,是直接通过输出流将图片进行返回了
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error(CodeMsg.MIAOSHA_FAIL);
        }
    }

}
