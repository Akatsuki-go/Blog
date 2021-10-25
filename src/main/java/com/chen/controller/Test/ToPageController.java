package com.chen.controller.Test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ToPageController {
    @RequestMapping("/")
    public String userLoginPage(){
        return "login";
    }
    @RequestMapping("/blogIndex")
    public String BlogIndexPage(){
        return "blogIndex";
    }
}
