package com.imooc.miaosha.vo;

import com.imooc.miaosha.domain.OrderInfo;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDetailVo implements Serializable {

    private static final long serialVersionUID = 2730093933516806600L;

    private GoodsVo goods;

    private OrderInfo order;

}
