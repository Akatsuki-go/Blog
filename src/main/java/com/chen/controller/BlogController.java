package com.chen.controller;

import com.chen.service.serviceInterface.ArticleCommentService;
import com.chen.service.serviceInterface.ArticleService;
import com.chen.service.serviceInterface.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Map;

@RestController
public class BlogController {
    @Autowired
    BlogService blogService;
    @Autowired
    ArticleService articleService;
    @Autowired
    ArticleCommentService articleCommentService;

    @PostMapping("/blog/side")
    public Map<String, Object> side (@RequestBody Map<String,String> map) throws ParseException {
//        System.out.println(map);
//        map.put("readStart",0);
//        map.put("readLen",5);
//        map.put("commentStart",0);
//        map.put("commentLen",5);
//        System.out.println(map);

        return blogService.BlogSide(map);

    }
}
