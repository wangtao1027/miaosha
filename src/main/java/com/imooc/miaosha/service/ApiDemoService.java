package com.imooc.miaosha.service;

import com.imooc.miaosha.dao.ApiDemoDao;
import com.imooc.miaosha.domain.ApiDemoEntity;
import com.imooc.miaosha.request.RequestParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ApiDemoService {

    @Resource
    private ApiDemoDao apiDemoDao;

    public List<ApiDemoEntity> findAll() {
        return apiDemoDao.findAll();
    }

    public List<ApiDemoEntity> findByLimit() {
        return apiDemoDao.findByLimit();
    }

    public List<ApiDemoEntity> findByStatus(int status) {
        return apiDemoDao.findByStatus(status);
    }

    public ApiDemoEntity findOne(RequestParam requestParam) {
        String goodsName = requestParam.getGoodsName();
        String goodsTitle = requestParam.getGoodsTitle();
        String goodsImg = requestParam.getGoodsImg();
        int status = requestParam.getStatus();
        return apiDemoDao.findOne(goodsName,goodsTitle,goodsImg,status);
    }

    public ApiDemoEntity selectByPrimaryKey(Long id) {
        return apiDemoDao.selectByPrimaryKey(id);
    }

}
