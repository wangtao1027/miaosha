package com.imooc.miaosha.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 秒杀订单实体类
 */
@Data
public class MiaoShaOrder implements Serializable {

    private static final long serialVersionUID = -8574528840812351874L;

    private Long id;

    private Long userId;

    private Long  orderId;

    private Long goodsId;
}
