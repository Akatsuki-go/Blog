package com.chen.controller;

import com.chen.entity.LoginUser;
import com.chen.entity.User;
import com.chen.service.serviceInterface.UserService;
import com.chen.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    //  登录
    @PostMapping("/login")
    public LoginUser login(@RequestBody LoginUser loginUser) {
        LoginUser u = userService.login(loginUser);
//        System.out.println("========user--toekn====");
//        System.out.println(u.getToken());
        return u;
    }

    //注册
    @PostMapping("/register")
    public String register(@RequestBody User user) throws ParseException {
        userService.register(user);
        return "200";
    }

    //获取个人信息
    @GetMapping("/userMessage/{id}")
    public User queryUserMessage(@PathVariable int id) {
        return userService.getUsermessage(id);
    }

    @GetMapping("/checkToken/{userId}")
    public Map<String, Object> checkToken(HttpServletRequest request,@PathVariable("userId") String userId){
//        System.out.println("===============");
//        System.out.println(request.getHeader("token").toString());
//        System.out.println(userId);
//                return null;
        return userService.checkToken(request.getHeader("token"), userId);
    }
}