package com.imooc.miaosha.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Goods implements Serializable {

    private static final long serialVersionUID = -4440174881390477556L;

    private Long id;
    private String goodsName;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDetail;
    private Double goodsPrice;
    private Integer goodsStock;

}
