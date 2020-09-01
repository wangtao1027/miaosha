package com.imooc.miaosha.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Api接口 测试类
 */
@Data
public class ApiDemoEntity implements Serializable {

    private static final long serialVersionUID = -2444135656842040736L;

    private Long id;
    private String goodsName;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDetail;
    private Double goodsPrice;
    private Integer goodsStock;
    private Integer goodsStatus;
}
