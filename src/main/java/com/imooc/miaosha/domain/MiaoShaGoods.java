package com.imooc.miaosha.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 秒杀商品实体列
 */
@Data
public class MiaoShaGoods implements Serializable {

    private static final long serialVersionUID = 3882085332993820019L;

    private Long id;

    private Long goodsId;

    private Integer stockCount;

    private Date startDate;

    private Date endDate;



}
