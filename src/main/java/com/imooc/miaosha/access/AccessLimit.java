package com.imooc.miaosha.access;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author wt
 * @version 1.0
 * @date 2020/9/23 8:43
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface AccessLimit {

    int second();       //秒数
    int maxCount();     //次数
    boolean needLogin() default true;   //是否需要登录

}
