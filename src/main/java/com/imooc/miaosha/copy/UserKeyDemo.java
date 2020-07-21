package com.imooc.miaosha.copy;

public class UserKeyDemo extends BaseKeyPrefix {

    public UserKeyDemo(String prefixStr) {
        super(prefixStr);
    }

    public static UserKeyDemo getById = new UserKeyDemo("id");
    public static UserKeyDemo getByName = new UserKeyDemo("name");

}
