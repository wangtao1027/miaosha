package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.Goods;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.redis.GoodsKey;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.GoodsService;
import com.imooc.miaosha.service.MiaoshaUserService;
import com.imooc.miaosha.vo.GoodsDetaiVo;
import com.imooc.miaosha.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 页面缓存,url缓存,对象缓存
 */
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

    @Autowired
    ThymeleafViewResolver thymeleafViewResolverf;

    @Autowired
    ApplicationContext applicationContext;

    /**
     * 页面级缓存处理
     *
     * @param request
     * @param response
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String list(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user) {
        logger.info("run method list");
        //从数据库中取
        model.addAttribute("user", user);

        //从缓存中取页面
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);

        //手动缓存
        SpringWebContext ctx = new SpringWebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
        html = thymeleafViewResolverf.getTemplateEngine().process("goods_list", ctx);
        if (!StringUtils.isEmpty(html)) {
            //不为空存储到redis当中
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        return html;
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

        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);

        return "goods_detail";
    }

    @RequestMapping("/getDetail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetaiVo> getDetail(MiaoshaUser user, @PathVariable("goodsId") long id) {
        logger.info(String.format("run method getDetail params=%s,user=%s", id, user.toString()));

        //根据商品id
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(id);

        int miaoshaStatus = 0;  // 状态:0,未开始,1,正在进行中,2已经结束
        int remainSeconds = 0;
        int startTime = (int) goods.getStartDate().getTime();
        int endTime = (int) goods.getEndDate().getTime();
        int curTime = (int) System.currentTimeMillis();

        if (curTime < startTime) {  //未开始
            miaoshaStatus = 0;
            remainSeconds = startTime - curTime / 1000;
        } else if (curTime > endTime) { //结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else { //正在进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }

        GoodsDetaiVo goodsDetaiVo = new GoodsDetaiVo();
        goodsDetaiVo.setUser(user);
        goodsDetaiVo.setGoods(goods);
        goodsDetaiVo.setMiaoshaStatus(miaoshaStatus);
        goodsDetaiVo.setRemainSeconds(remainSeconds);

        return Result.success(goodsDetaiVo);
    }

    /**
     * url级缓存
     *
     * @param request
     * @param response
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/to_detail2/{goodsId}", produces = "text/html")
    @ResponseBody
    public String detail2(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user, @PathVariable("goodsId") long goodsId) {

        logger.info("run method detail2");
        model.addAttribute("user", user);

        //从缓存中取
        String html = redisService.get(GoodsKey.getGoodsDetail, "" + goodsId, String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }

        //手动渲染
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);

        long startTime = goods.getStartDate().getTime();    //秒杀开始时间
        long endTime = goods.getEndDate().getTime();    //秒杀结束时间
        long curTime = System.currentTimeMillis();      //当前系统时间

        int remainSeconds = 0;      //商品距离秒杀还有多久才开始
        int miaoshaStatus = 0;      //商品秒杀状态 0:未开始,1:正在进行当中,2:已结束
        if (curTime < startTime) {
            //秒杀还未开始
            miaoshaStatus = 0;
            remainSeconds = (int) (startTime - curTime) / 1000;
        } else if (curTime > endTime) {
            //秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {
            //正在进行当中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }

        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("miaoshaStatus", miaoshaStatus);

        //手动渲染
        SpringWebContext ctx = new SpringWebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
        html = thymeleafViewResolverf.getTemplateEngine().process("goods_detail", ctx);
        //不为空存储到缓存中
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsDetail, "" + goodsId, html);
        }

        return html;
    }

    /**
     * 批量导入
     *
     * @param goodsList
     * @return
     */
    @RequestMapping("/batchImport")
    @ResponseBody
    public Result<String> batchImport(@RequestBody List<Goods> goodsList) {
        logger.info(String.format("run method param=%s", goodsList.toString()));
        if (goodsList == null || goodsList.size() == 0) {
            return Result.error(CodeMsg.PARAM_EMPTY);
        }

        return Result.success("成功");
    }

    /**
     * 批量查询
     *
     * @return
     */
    @RequestMapping("/batchQuery")
    @ResponseBody
    public Result<List<Goods>> batchQuery() {
        logger.info("run method batchQuery");
        List<String> idList = new ArrayList<String>();
        for (int i = 1; i <= 4; i++) {
            idList.add(i + "");
        }
        List<Goods> goods = goodsService.batchQuery(idList);
        return Result.success(goods);
    }

    /**
     * 批量插入
     * @param goodsList
     * @return
     */
    @RequestMapping("/batchInsert")
    @ResponseBody
    public Result<String> batchInsert(@RequestBody List<Goods> goodsList) {
        logger.info(String.format("run method batchInsert param=%s",goodsList.toString()));
        int i = goodsService.batchInsert(goodsList);
        return Result.success(CodeMsg.SUCCESS.getMsg());
    }

    /**
     * 批量修改
     * @param goodsList
     * @return
     */
    @RequestMapping("/batchUpdate")
    @ResponseBody
    public Result<String> batchUpdate(@RequestBody List<Goods> goodsList) {
        logger.error(String.format("run method batchUpdate param=%s",goodsList.toString()));
        return Result.success(CodeMsg.SUCCESS.getMsg());
    }
}
