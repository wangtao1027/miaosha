package com.imooc.miaosha.request;

import java.io.Serializable;

public class RequestParam implements Serializable {

    private static final long serialVersionUID = -291706683588357834L;

    private String goodsName;
    private String goodsTitle;
    private String goodsImg;
    private int status;

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RequestParam{" +
                "goodsName='" + goodsName + '\'' +
                ", goodsTitle='" + goodsTitle + '\'' +
                ", goodsImg='" + goodsImg + '\'' +
                ", status=" + status +
                '}';
    }
}
