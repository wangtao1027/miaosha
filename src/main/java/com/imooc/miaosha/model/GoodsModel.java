package com.imooc.miaosha.model;

import com.imooc.miaosha.util.Excel;

import java.io.Serializable;
import java.lang.reflect.Field;

public class GoodsModel implements Serializable {

    @Excel(title = "编号")
    private long id;

    @Excel(title = "商品名称")
    private String goodsName;

    @Excel(title = "商品标题")
    private String goodsTitle;

    @Excel(title = "商品图片")
    private String goodsImg;

    @Excel(title = "商品详情")
    private String goodsDetail;

    @Excel(title = "商品单价")
    private Double goodsPrice;

    @Excel(title = "库存数量")
    private Integer goodsStock;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsStock() {
        return goodsStock;
    }

    public void setGoodsStock(Integer goodsStock) {
        this.goodsStock = goodsStock;
    }

    @Override
    public String toString() {
        return "GoodsModel{" +
                "id=" + id +
                ", goodsName='" + goodsName + '\'' +
                ", goodsTitle='" + goodsTitle + '\'' +
                ", goodsImg='" + goodsImg + '\'' +
                ", goodsDetail='" + goodsDetail + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", goodsStock=" + goodsStock +
                '}';
    }

    /**
     * 解析字段注解
     *
     * @param clazz
     */
    public static <T> void parseField(Class<T> clazz) throws Exception {

        Field field;
        Field[] fields=clazz.getDeclaredFields();
        for (int i = 0; i <fields.length ; i++) {
            fields[i].setAccessible(true);
        }
        for (int i = 0; i <fields.length ; i++) {
            field=clazz.getDeclaredField(fields[i].getName());
            Excel column=field.getAnnotation(Excel.class);
            if(column!=null){
                System.out.println(fields[i].getName()+":"+column.title());
            }
        }
    }

}
