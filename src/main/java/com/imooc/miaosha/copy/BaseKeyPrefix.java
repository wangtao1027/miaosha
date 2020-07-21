package com.imooc.miaosha.copy;

public abstract class BaseKeyPrefix implements Key {

    private int expirationTime;

    private String prefixStr;

    public BaseKeyPrefix(String prefixStr) {   //设置为0默认永不过期
        this(0, prefixStr);
    }

    public BaseKeyPrefix(int expirationTime, String prefixStr) {
        this.expirationTime = expirationTime;
        this.prefixStr = prefixStr;
    }

    @Override
    public int expirationTime() {
        return 0;
    }

    @Override
    public String getKeyPrefix() {
        String simpleName = getClass().getSimpleName();
        return simpleName + ":" + prefixStr;
    }
}
