package com.chen.service;

import com.chen.dao.ArticleCommentMapper;
import com.chen.dao.ArticleMapper;
import com.chen.entity.Comment;
import com.chen.entity.CommentPage;
import com.chen.entity.Page;
import com.chen.service.serviceInterface.ArticleCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ArticleCommentServiceImpl implements ArticleCommentService {
    @Autowired
    ArticleCommentMapper articleCommentMapper;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    RedisServiceImpl redisService;

    @Override
    @Transactional
    public int addArticleComment(Comment comment) {
        comment.setCreateTime(new Date());
        //文章评论数+1
        try {
            articleMapper.updateArticleCommentNum(1, comment.getForId());
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        redisService.addTopLastlyComment(String.valueOf(comment.getUserId()), String.valueOf(comment.getId()));
        return articleCommentMapper.insertArticleComment(comment);
    }

    @Override
    public Page listArticleCommentsTopOrderByNewTimeLimit(int pageNo, int len,String userId) {
        int count=articleCommentMapper.countAriComNum(userId);
        int start=(pageNo-1)*len;
        int end=start+len;
        List<Map<String, Object>> list = articleCommentMapper.selectArticleCommentsLatelyByUserId(userId, start, len);
//        List<Map<String, Object>> list = null;
        Page page = new Page(len,count,start,end,list,pageNo);

        System.out.println("AComPage: "+page);
        return page;
    }

    @Override
    public List<Comment> listArticleCommentsLately(Map<String, Object> map) {
        Integer userId = (Integer) map.get("userId");
        Integer start= (Integer) map.get("start");
        Integer len= (Integer) map.get("len");
//        return articleCommentMapper.selectArticleCommentsLatelyByUserId(String.valueOf(userId), start, len);
        return null;
    }
    //文章评论分页
    @Override
    @Transactional
    public CommentPage listArticleCommentPage(String userId, int articleId, int PageNo, int PageSize) {
//        System.out.println(userId);
//        System.out.println(articleId);
//        System.out.println(PageNo);
//        System.out.println(PageSize);

        int start = (PageNo - 1) * PageSize;
        List<Map<String, Object>> articles = articleCommentMapper.selectArticleCommentPageByArticleId(articleId,start, PageSize);
        List<String> idList = new ArrayList<>();

        for (Map<String, Object> article : articles) {
            String articComId = String.valueOf(article.get("id"));
            idList.add(articComId);
        }

        //isGoodList
        List<Boolean> goodList = redisService.getComGoodPage(idList, userId,"artCom");
        //        System.out.println(" "+articles);
        //goodNUmList
        List<Integer> goodNumList = redisService.getPageComGoodNum(idList,"artCom");
        int count = articleCommentMapper.countArticleCommentByArticleId(articleId);

        CommentPage CommentPage = new CommentPage(PageSize,count, start, PageSize,
                articles,PageNo,goodList,goodNumList);

        System.out.println(CommentPage);
        return CommentPage;
    }

}
