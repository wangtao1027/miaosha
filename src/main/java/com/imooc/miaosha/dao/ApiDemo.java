package com.imooc.miaosha.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.miaosha.domain.ApiDemoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Api商品表操作
 */
@Mapper
public interface ApiDemo extends BaseMapper<ApiDemo> {

    int deleteByPrimaryKey(Long id);

    int insert(ApiDemoEntity apiDemoEntity);

    int insertSelective(ApiDemoEntity apiDemoEntity);

    ApiDemoEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApiDemoEntity apiDemoEntity);

    int updateByPrimaryKeyWithBLOBs(ApiDemoEntity apiDemoEntity);

    int updateByPrimaryKey(ApiDemoEntity apiDemoEntity);
}
