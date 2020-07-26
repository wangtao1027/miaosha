package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.service.MiaoshaUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    private final static Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private MiaoshaUserService userService;

    @Autowired
    private RedisService redisService;

    /*@RequestMapping("/to_list")
    public String list(Model model, MiaoshaUser user) {
        logger.error(user.toString());
        model.addAttribute("user",user);
        return "goods_list";
    }*/

    //假如现在什么也不做
    //为了兼容手机端,是直接从request请求域中取的cookie,这里提供两种取值方式,然后做一个优先级判断
    //首先去param,取不到然后再去取cookie中的token
    @RequestMapping("/to_list")
    public String list(Model model, MiaoshaUser user
//                       @CookieValue(value = MiaoshaUserService.COOKI_NAME_TOKEN,required = false) String cookieToken,
//                       @RequestParam(value = MiaoshaUserService.COOKI_NAME_TOKEN,required = false) String paramToken
                     ){
//        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
//            //如果都为空则返回登录页面
//            return "login";
//        }
//
//        String token = StringUtils.isEmpty(paramToken) ? cookieToken:paramToken;    //如果参数中为空,则从cookie中开始取
//        MiaoshaUser user = userService.getByToken(response,token);
        model.addAttribute("user",user);
        return "goods_list";
    }

}
