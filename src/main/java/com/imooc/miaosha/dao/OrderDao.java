package com.imooc.miaosha.dao;

import com.imooc.miaosha.domain.MiaoShaOrder;
import com.imooc.miaosha.domain.OrderInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderDao {

    @Select("select * from miaosha_order where user_id = #{userId} and order_id = #{orderId}")
    MiaoShaOrder getMiaoshaOrderByUserIdGoodsId(@Param("userId") Long userId, @Param("goodsId") Long goodsId);

    //向订单表中添加数据
    @Insert("insert into order_info (user_id,goods_id,goods_name,goods_count,goods_price,order_channel,status,create_date) values(" +
            "#{userId},#{goodsId},#{goodsName},#{goodsCount},#{goodsPrice},#{orderChannel},#{status},#{createDate})")
    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = long.class,before=false,statement = "select last_insert_id()")
    long insert(OrderInfo orderInfo);

    //向中间表中添加数据
    @Insert("insert into miaosha_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
    int insertMiaoshaOrder(MiaoShaOrder miaoShaOrder);

}
