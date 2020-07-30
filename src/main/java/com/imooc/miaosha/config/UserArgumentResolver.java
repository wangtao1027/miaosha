package com.imooc.miaosha.config;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.service.MiaoshaUserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private MiaoshaUserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> clazz = parameter.getParameterType();  //如果类型是MiaoShaUser,的时候返回为true,才会做下面的处理
        return clazz == MiaoshaUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);

        String paramToken = request.getParameter(MiaoshaUserService.COOKI_NAME_TOKEN);
        String cookieToken = getCookieValue(request, MiaoshaUserService.COOKI_NAME_TOKEN);
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            //如果都为空则返回登录页面
            return "login";
        }

        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;    //如果参数中为空,则从cookie中开始取
        return userService.getByToken(response, token);  //这里面response可能是一个空的
    }

    private String getCookieValue(HttpServletRequest request, String cookiName) {

        Cookie[] cookies = request.getCookies();    //遍历所以cookie
        if (cookies == null || cookies.length <= 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookiName)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
