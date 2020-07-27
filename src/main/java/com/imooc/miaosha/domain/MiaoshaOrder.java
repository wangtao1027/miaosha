package com.imooc.miaosha.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class MiaoshaOrder implements Serializable {

    private static final long serialVersionUID = -8882648079250316930L;

    private Long id;
    private Long userId;
    private Long  orderId;
    private Long goodsId;

}
