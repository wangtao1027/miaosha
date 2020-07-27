package com.imooc.miaosha.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OderInfo implements Serializable {

    private static final long serialVersionUID = 545235551339249184L;

    private Long id;
    private Long userId;
    private Long goodsId;
    private Long  deliveryAddrId;
    private String goodsName;
    private Integer goodsCount;
    private Double goodsPrice;
    private Integer orderChannel;
    private Integer status;
    private Date createDate;
    private Date payDate;

}
