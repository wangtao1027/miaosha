package com.imooc.miaosha.service;

import com.imooc.miaosha.dao.GoodsDao;
import com.imooc.miaosha.domain.MiaoShaGoods;
import com.imooc.miaosha.vo.GoodsVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsService {

    @Resource
    private GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo() {
        return goodsDao.listGoodsVo();
    }

    public GoodsVo getGoodsVoByGoodsId(Long id) {
        return goodsDao.getGoodsVoByGoodsId(id);
    }

    //扣减库存
    public int reduceStock(GoodsVo goodsVo) {
        MiaoShaGoods miaoShaGoods = new MiaoShaGoods();
        miaoShaGoods.setId(goodsVo.getId());
        return goodsDao.reduceStock(miaoShaGoods);
    }

}
