package com.imooc.miaosha.vo;

import com.imooc.miaosha.validator.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class LoginVo {

    @NotNull
    @IsMobile   //自定义注解
    private String mobile;

    @NotNull
    @Length(min = 32)   //长度最小为32
    private String password;
}
