package com.imooc.miaosha.dao;

import com.imooc.miaosha.domain.Goods;
import com.imooc.miaosha.domain.MiaoShaGoods;
import com.imooc.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GoodsDao {

    @Select("select g.*,mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date from miaosha_goods mg left join goods g on mg.goods_id = g.id")
    List<GoodsVo> listGoodsVo();

    @Select("select g.*,mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date from miaosha_goods mg left join goods g on mg.goods_id = g.id where g.id = #{id}")
    GoodsVo getGoodsVoByGoodsId(@Param("id") Long id);

    //扣减库存,库存为0截止操作,添加一个判断条件,这样数据库就会加一个锁,防止两个线程争强,导致库存为负数
    @Update("update miaosha_goods set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count > 0")
    int reduceStock(MiaoShaGoods goods);

    @Update("update miaosha_goods set stock_count = #{stockCount} where goods_id = #{goodsId}")
    int resetStock(MiaoShaGoods goods);

    int selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int insert(Goods goods);

    int insertSelective(Goods goods);

    int updateByPrimaryKeySelective(Goods goods);

    int updateByPrimaryKeyWithBLOBs(Goods goods);

    int updateByPrimaryKey(Goods goods);

    //批量查询
    List<Goods> batchQuery(@Param("idList") List<String> idList);

    //批量插入
    int batchInsert(@Param("goodsList") List<Goods> goodsList);

    //批量修改
    int batchUpdate(@Param("goodsList") List<Goods> goodsList);

}
