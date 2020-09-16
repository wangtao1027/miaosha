package com.imooc.miaosha.service;

import com.imooc.miaosha.dao.GoodsDao;
import com.imooc.miaosha.domain.Goods;
import com.imooc.miaosha.domain.MiaoShaGoods;
import com.imooc.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsService {

    private final static Logger logger = LoggerFactory.getLogger(GoodsService.class);

    @Resource
    private GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo() {
        return goodsDao.listGoodsVo();
    }

    public GoodsVo getGoodsVoByGoodsId(Long id) {
        return goodsDao.getGoodsVoByGoodsId(id);
    }

    //扣减库存(是否成功)
    public Boolean reduceStock(GoodsVo goodsVo) {
        MiaoShaGoods miaoShaGoods = new MiaoShaGoods();
        miaoShaGoods.setGoodsId(goodsVo.getId());
        int i = goodsDao.reduceStock(miaoShaGoods);
        return i > 0;
    }

    //重置数据库中所有商品的库存为10
    public void resetStock(List<GoodsVo> goodsVoList) {
        for (GoodsVo goodsVo : goodsVoList) {
            MiaoShaGoods miaoShaGoods = new MiaoShaGoods();
            miaoShaGoods.setGoodsId(goodsVo.getId());
            miaoShaGoods.setStockCount(goodsVo.getStockCount());

            logger.error(miaoShaGoods.toString());
            goodsDao.resetStock(miaoShaGoods);
        }
    }

    //批量查询
    public List<Goods> batchQuery(List<String> idList) {
        return goodsDao.batchQuery(idList);
    }

    //批量插入
    public int batchInsert(List<Goods> goodsList) {
        return goodsDao.batchInsert(goodsList);
    }

    //批量更新
    public int batchUpdate(List<Goods> goodsList) {
        return goodsDao.batchUpdate(goodsList);
    }

}
