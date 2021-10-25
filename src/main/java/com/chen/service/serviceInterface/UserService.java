package com.chen.service.serviceInterface;

import com.chen.entity.LoginUser;
import com.chen.entity.User;

import java.text.ParseException;
import java.util.Map;

public interface UserService {
    void register(User user) throws ParseException;
    LoginUser login(LoginUser user);
    User getUsermessage(int id);
    Map<String, Object> checkToken(String token,String userId);
}
