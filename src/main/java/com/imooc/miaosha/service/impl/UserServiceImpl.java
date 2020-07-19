package com.imooc.miaosha.service.impl;

import com.imooc.miaosha.dao.UserDao;
import com.imooc.miaosha.domain.User;
import com.imooc.miaosha.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public User getUser(int id) {
        return userDao.getUser(id);
    }

    @Override
    public boolean add(User user) {
        return userDao.add(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)   //加了注解之后事务回滚
    public boolean addTx() {
        //定义插入两个User对象
        User user1 = new User(5,"王涛");
        User user2 = new User(1,"崔硕果");
        User user3 = new User(6,"韩翔");

        //插入
        userDao.addTx(user1);
        userDao.addTx(user2);
        userDao.addTx(user3);

        return true;
    }
}
