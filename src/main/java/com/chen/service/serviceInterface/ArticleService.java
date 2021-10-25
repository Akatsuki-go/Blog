package com.chen.service.serviceInterface;

import com.chen.entity.Article;
import com.chen.entity.ArticlePage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ArticleService {
    List<Article> listArticleAll();
    Map<String, Object> getArticleById(String id, String userId);
    ArticlePage removeArticleForId(HashMap<String, Object> map);
    int saveArticle(Article article);
    int updateArticleById(Article article);

    ArticlePage listArticlePage(int PageNo, int PageSize, int userId);
    List<Map<String, Object>> getRecommendArticle(Map<String, Object> map);
}
