package com.chen.service;

import com.chen.dao.ArticleCommentMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class ArticleCommentServiceImplTest {
   @Autowired
   ArticleCommentServiceImpl articleCommentService;
    @Autowired
    ArticleCommentMapper articleCommentMapper;
   @Test
   public void testAC(){
       HashMap<String, Object> map = new HashMap<>();
       map.put("userId", 3);
       map.put("pageNo", 1);
       map.put("pageSize", "3");
       System.out.println(articleCommentService.listArticleCommentsTopOrderByNewTimeLimit(18, 3, "3"));
   }
   @Test
   public void testACMapper(){
       System.out.println(articleCommentMapper.selectArticleCommentsLatelyByUserId("3", 0, 3));
   }
}