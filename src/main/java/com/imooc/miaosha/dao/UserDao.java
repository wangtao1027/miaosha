package com.imooc.miaosha.dao;

import com.imooc.miaosha.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {

    @Select("select * from user where id = ${id}")
    User getUser(@Param("id") int id);

    @Insert("insert into user values (#{user.id},#{user.name})")
    boolean add(@Param("user") User user);

    /**
     * 测试数据库事务
     * @param user
     * @return
     */
    @Insert("insert into user values (#{id},#{name})")
    boolean addTx(User user);

}
