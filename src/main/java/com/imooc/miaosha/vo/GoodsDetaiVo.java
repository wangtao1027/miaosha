package com.imooc.miaosha.vo;

import com.imooc.miaosha.domain.MiaoshaUser;
import lombok.Data;

import java.io.Serializable;

@Data
public class GoodsDetaiVo implements Serializable {

    private static final long serialVersionUID = -876086397126184224L;

    private int miaoshaStatus = 0;
    private int remainSeconds = 0;
    private GoodsVo goods ;
    private MiaoshaUser user;

}
