package com.imooc.miaosha.service;

import com.imooc.miaosha.domain.User;

public interface UserService {

    User getUser(int id);

    boolean add(User user);

    boolean addTx();

}
