package com.imooc.miaosha.rabbitmq;

import com.imooc.miaosha.domain.MiaoshaUser;
import lombok.Data;

/**
 * @author wt
 * @version 1.0
 * @date 2020/9/10 8:35
 */
@Data
public class MiaoshaMessage {

    private MiaoshaUser user;
    private long goodsId;

}
