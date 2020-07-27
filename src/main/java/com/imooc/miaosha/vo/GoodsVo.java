package com.imooc.miaosha.vo;

import com.imooc.miaosha.domain.Goods;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class GoodsVo extends Goods implements Serializable {

    private static final long serialVersionUID = -2228309070783170130L;

    private Double miaoshaPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;

}
