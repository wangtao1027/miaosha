package com.imooc.miaosha.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wt
 * @version 1.0
 * 请求参数实例
 * @date 2020/9/11 16:14
 */
@Data
public class MessageParam implements Serializable {

    private static final long serialVersionUID = 8949580829747279388L;

    private String orgCode;
    private String params;
    private String serviceCode;
    private String tokenValue;
}
