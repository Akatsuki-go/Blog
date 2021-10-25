package com.chen.service;

import com.chen.dao.UserMapper;
import com.chen.entity.LoginUser;
import com.chen.entity.User;
import com.chen.service.serviceInterface.UserService;
import com.chen.utils.JwtUtils;
import com.chen.utils.UserUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserSeviceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public void register(User user) throws ParseException {
        user.setNewDate(new Date());
        userMapper.insertUser(user);
    }

    @Override
    public LoginUser login(LoginUser user) {
        String pw = userMapper.selectPasswordByAccount(user.getAccountName());
        LoginUser reUser = userMapper.selectUserByAccount(user.getAccountName());
        ;

        if (pw.equals(user.getPassword())) {
//            System.out.println("LoginUser:"+reUser);
            String token = JwtUtils.createToken(reUser.getUserId(), reUser.getUserName());
            reUser.setToken(token);
//            System.out.println(token);
            return reUser;
        } else {
            return new LoginUser();
        }
    }

    @Override
    public User getUsermessage(int id) {
        return userMapper.selectUserById(id);
    }

    @Override
    public Map<String, Object> checkToken(String token, String accessUserId) {
//        System.out.println(token);
//        System.out.println(accessUserId);
        Map<String, Object> map = new HashMap<>();
        Boolean check = (Boolean) map.put("check", JwtUtils.checkToken(token));
        if (Boolean.FALSE.equals(check)){
            map.put("isMyBlog",null);
            map.put("nowUserId", null);
            map.put("nowUserName", null);
            return map;
        }
        //返回nowUerId，nowUserName，isMyBlog
        map.put("isMyBlog",JwtUtils.isMyBlog(token, accessUserId));
        map.put("nowUserId", accessUserId);
        map.put("nowUserName", userMapper.selectUserNameById(accessUserId));

        return map;
    }
}
