package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.redis.GoodsKey;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.GoodsService;
import com.imooc.miaosha.service.MiaoshaService;
import com.imooc.miaosha.service.OrderService;
import com.imooc.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    public Map<Long, Boolean> localMap = new HashMap<Long, Boolean>();

    /**
     * 系统初始化执行方法
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        if (goodsList == null) {
            return;
        }
        for (GoodsVo goodsVo : goodsList) {
            redisService.set(GoodsKey.getMiaoshaGoodsStock,""+goodsVo.getId(),goodsVo.getStockCount());
            localMap.put(goodsVo.getId(),false);
        }

    }

    @RequestMapping("/do_miaosha")
    @ResponseBody
    public Result<OrderInfo> miaosha(Model model, MiaoshaUser user, @RequestParam("goodsId") Long goodsId) {
        logger.info(String.format("run method miaosha param=%s", goodsId));
        model.addAttribute("user", user);
        //判断用户是否登录
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
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

        return null;
    }

}
