package com.chen.dao;

import com.chen.entity.LoginUser;
import com.chen.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> selectUserAll();
    //可能User里不包括一些密码账号的隐私信息
    User selectUserById(int id);
    String selectUserNameById(String id);

    int deleteUserById(int id);
    int updateUser(User user);
    int insertUser(User user);

    String selectPasswordByAccount(String accountName);
    String selectUserNameByAccount(String accountName);
    LoginUser selectUserByAccount(String accountName);


}
