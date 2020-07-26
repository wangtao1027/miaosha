package com.imooc.miaosha.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {IsMobileValidator.class})    //使用校验器类
public @interface IsMobile {

    boolean required() default true;    //参数默认不可以为空，但是有些场景下允许为空

    String message() default "手机号码格式错误";    //如果校验不通过的情况下的提示信息

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
