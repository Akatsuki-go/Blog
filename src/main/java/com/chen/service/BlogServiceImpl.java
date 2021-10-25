package com.chen.service;

import com.chen.dao.ArticleCommentMapper;
import com.chen.dao.ArticleMapper;
import com.chen.service.serviceInterface.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    ArticleCommentMapper articleCommentMapper;
    @Autowired
    RedisServiceImpl redisService;

    /*
    阅读量top
    最近评论top
     */
    @Override
    public Map<String, Object> BlogSide(Map<String, String> map) throws ParseException {
        int commentStart = Integer.parseInt(map.get("commentStart"));
        int commentLen = Integer.parseInt(map.get("commentLen"));
        String userId = map.get("userId");
        long readStart = Long.parseLong(map.get("readStart"));
        int readLen = Integer.parseInt(map.get("readLen"));

        Map<String, Object> results = new HashMap<>();
        /*原来：mysql*/
//        List<Map<String, Object>> readTopList = articleMapper.selectTitleTopOrderByReadLimit(readStart,readLen,userId);

        /*redis */
        //阅读排行榜
        List<Map<String, Object>> readTopList = new ArrayList<>();
        List<Map<String, Object>> articleList = redisService.getTopReCommArticle(userId, readStart, readLen);
        for (Map<String, Object> articleMap : articleList) {
            String articleId = (String) articleMap.get("articleId");
            Map<String, Object> readTopList1 = articleMapper.selectTopArticleById(articleId);
            readTopList.add(readTopList1);
        }

        //评论排行榜
//        List<Map<String, Object>> commentList=new ArrayList<>();
//        List<Map<String, Object>> tempCommentList = redisService.getTopLastlyComment(userId, commentStart, commentLen);
//        for (Map<String, Object> commentMap : tempCommentList) {
//            String commentId = (String) commentMap.get("commentId");
////            long time = (long) commentMap.get("time");
//            Map<String, Object> map1 = articleCommentMapper.selectTopCommentById(commentId);
//            commentList.add(map1);
//        }
        List<Map<String, Object>> commentList = articleCommentMapper.selectCommentTopOrderByNewTimeLimit(commentStart, commentLen, userId);

        results.put("readTopList", readTopList);
        results.put("commentList", commentList);

        System.out.println(results);
//        System.out.println(results+"===");
        return results;
    }
}
