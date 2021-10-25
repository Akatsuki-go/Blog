package com.chen.service;

import com.chen.dao.ArticleMapper;
import com.chen.entity.Article;
import com.chen.entity.ArticlePage;
import com.chen.service.serviceInterface.ArticleService;
import com.chen.utils.RedisUtil;
import com.chen.utils.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    RedisServiceImpl redisService;
//    @Autowired
//    RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<Map<String, Object>> getRecommendArticle(Map<String, Object> map) {
        long start = TypeUtils.IntegerToLong(map.get("start"));
        long end = TypeUtils.IntegerToLong(map.get("end"));

        /*redis*/
        List<Map<String, Object>> list = redisService.getRecommendArticle(start, end);
//        System.out.println(list);

        for (Map<String, Object> tempMap : list) {
            Integer readNum = TypeUtils.DoubleToInteger(tempMap.get("readNum"));
            tempMap.put("readNum", readNum);
//            System.out.println(readNum);
            String id = (String) tempMap.get("id");
            /*mysql*/
            Map<String, Object> ArtMap = articleMapper.selectRecomArticle(id);
            if (ArtMap != null)
                tempMap.putAll(ArtMap);
        }
        return list;
    }

    @Override
    public List<Article> listArticleAll() {
        return articleMapper.selectArticleAll();
    }

    @Override
    public Map<String, Object> getArticleById(String id, String userId) {
        /*改为用sortSet存储articleReadNum*/
//        articleMapper.updateReadNum(1, id);
        Map<String, Object> ArticleMap = new HashMap<>();
        Integer articleReadNum=null;
        ArticleMap = articleMapper.selectArticleById(Integer.parseInt(id));

        /*
        * 处理redis中文章信息没删除干净的情况
        * */
        if (ArticleMap==null){
            redisService.delArticleReadNum(id, userId);
        }

        /*redis的阅读量Inrc*/
        redisService.articleReadNumInrc(userId, id, 1);

        articleReadNum = redisService.getArticleReadNum(id);
        ArticleMap.put("readNum", articleReadNum);
        ArticleMap.put("state", true);
        return ArticleMap;
    }

    @Override
    @Transactional
    public ArticlePage removeArticleForId(HashMap<String, Object> map) {
        String articleId = String.valueOf(map.get("articleId"));
        System.out.println("articleId: "+articleId);
        /*mysql*/
        try {
            articleMapper.deleteArticleForID(Integer.parseInt(articleId));
//            int a=8/0;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            ArticlePage articlePage = new ArticlePage();
            articlePage.setState(false);
            return articlePage;
        }
//        System.out.println("mysql ok");
        String id = null;
        try {
            id = articleMapper.selectUserIdByArticleId(articleId);
        } catch (Exception e) {
            e.printStackTrace();
            ArticlePage articlePage = new ArticlePage();
            articlePage.setState(false);
            return articlePage;
        }
        /*redis*/
        redisService.delArticleReadNum(articleId,id);
        int userId = Integer.parseInt((String) map.get("userId"));
        int pageNo = (int) map.get("pageNo");
        int pageSize = (int) map.get("pageSize");
        return this.listArticlePage(pageNo, pageSize, userId);
    }

    @Override
    public int saveArticle(Article article) {
//        article.setCreateTime(new Date());
        return articleMapper.insertArticle(article);
    }

    @Override
    public int updateArticleById(Article article) {
        return articleMapper.updateArticleById(article);
    }

    @Override
    public ArticlePage listArticlePage(int PageNo, int PageSize, int userId) {
        int start = (PageNo - 1) * PageSize;
        List<Map<String, Object>> articles = articleMapper.selectArticlePage(start, PageSize, userId);
//        System.out.println(articles);
        /*文章页的articleIdList*/
        List<String> list = new ArrayList<>();
        for (Map<String, Object> article : articles) {
            list.add(String.valueOf(article.get("articleId")));
        }
        //GoodNumPage List
        List<Integer> pageReadNumList = redisService.getPageReadNum(list);
//        System.out.println(pageReadNumList);
        int count = articleMapper.countArticle(userId);
        //        articlePage.setPageSize(PageSize);
//        articlePage.setPageNo(PageNo);
        return new ArticlePage(PageSize, count, start, start + PageSize, articles, PageNo, pageReadNumList);
    }
}
