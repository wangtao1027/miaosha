package com.imooc.miaosha.controller;

import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.MiaoshaUserService;
import com.imooc.miaosha.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private MiaoshaUserService userService;

    /**
     * 跳转到login页面
     * @return
     */
    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response,@Valid LoginVo loginVo) {
        logger.info(loginVo.toString());    //d3b1294a61a07da9b49b6e22b2cbd7f9
        userService.login(response,loginVo);
        return Result.success(true);
    }

}
