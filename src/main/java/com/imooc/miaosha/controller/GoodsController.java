package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.service.GoodsService;
import com.imooc.miaosha.service.MiaoshaUserService;
import com.imooc.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    private final static Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private MiaoshaUserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/to_list")
    public String list(Model model, MiaoshaUser user) {
        logger.info("run method list");
        model.addAttribute("user", user);
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }

    @RequestMapping("/to_detail/{goodsId}")
    public String detail(Model model, MiaoshaUser user, @PathVariable("goodsId") Long id) {
        logger.info(String.format("run method detail params= %s", id));
        model.addAttribute("user", user);
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(id);
        model.addAttribute("goods", goods);

        long startTime = goods.getStartDate().getTime();        //秒杀开始时间
        long endTime = goods.getEndDate().getTime();            //秒杀结束时间
        long curTime = System.currentTimeMillis();

        int remainSeconds = 0; //时间差
        int miaoshaStatus = 0; //商品状态
        if (curTime < startTime) {  //未开始
            miaoshaStatus = 0;
            remainSeconds = (int) ((startTime - curTime) / 1000);
        } else if (curTime > endTime) { //结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {    //正在进行当中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }

        model.addAttribute("miaoshaStatus",miaoshaStatus);
        model.addAttribute("remainSeconds",remainSeconds);

        return "goods_detail";
    }

}
