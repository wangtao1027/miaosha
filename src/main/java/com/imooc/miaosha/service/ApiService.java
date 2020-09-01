package com.imooc.miaosha.service;

import com.imooc.miaosha.dao.ApiDemo;
import com.imooc.miaosha.domain.ApiDemoEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wt
 * @version 1.0
 * @date 2020/8/28 10:51
 */
@Service
public class ApiService {

    @Resource
    private ApiDemo apiDemo;

    public int deleteByPrimaryKey(Long id) {
        return apiDemo.deleteByPrimaryKey(id);
    }

    public int insert(ApiDemoEntity apiDemoEntity) {
        return apiDemo.insert(apiDemoEntity);
    }

    public int insertSelective(ApiDemoEntity apiDemoEntity) {
        return apiDemo.insertSelective(apiDemoEntity);
    }

    public ApiDemoEntity selectByPrimaryKey(Long id) {
        return apiDemo.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(ApiDemoEntity apiDemoEntity) {
        return apiDemo.updateByPrimaryKeySelective(apiDemoEntity);
    }

    public int updateByPrimaryKeyWithBLOBs(ApiDemoEntity apiDemoEntity) {
        return apiDemo.updateByPrimaryKeyWithBLOBs(apiDemoEntity);
    }

    public int updateByPrimaryKey(ApiDemoEntity apiDemoEntity) {
        return apiDemo.updateByPrimaryKey(apiDemoEntity);
    }

}
