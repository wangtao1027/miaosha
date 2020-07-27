package com.imooc.miaosha.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MiaoshaGoods implements Serializable {

    private static final long serialVersionUID = 2732998320440041185L;

    private Long id;
    private Long goodsId;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;

}
