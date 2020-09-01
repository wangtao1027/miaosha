package com.imooc.miaosha.dao;

import com.imooc.miaosha.domain.ApiDemoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ApiDemoDao {

    //获取全部数据
    @Select("select * from api_demo")
    public List<ApiDemoEntity> findAll();

    //获取前10条数据
    @Select("select * from api_demo limit 10")
    public List<ApiDemoEntity> findByLimit();

    @Select("select * from api_demo where status = #{status}")
    public List<ApiDemoEntity> findByStatus(@Param("status") int status);

    @Select("select * from api_demo where goods_name = #{goodName} and goods_title = #{goodTitle} " +
            "and goods_img = #{goodsImg} and status = #{status}")
    public ApiDemoEntity findOne(@Param("goodName") String goodName, @Param("goodTitle") String goodTitle, @Param("goodsImg") String goodsImg, @Param("status") int status);

    @Select("select * from api_demo where id = #{id}")
    public ApiDemoEntity selectByPrimaryKey(@Param("id") Long id);

}
