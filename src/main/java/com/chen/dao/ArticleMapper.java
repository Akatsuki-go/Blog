package com.chen.dao;

import com.chen.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ArticleMapper {
    List<Article> selectArticleAll();
    Map<String, Object> selectArticleById(int id);
    int deleteArticleForID(int id);
    int insertArticle(Article article);
    int updateArticleById(Article article);

    List<Map<String, Object>> selectTitleTopOrderByReadLimit(@Param("start") int start, @Param("len") int len,@Param("id") String articleId);
    Map<String, Object> selectTopArticleById(@Param("id") String articleId);
    int countArticle(int userId);

//    int updateReadNum(@Param("n") int n,@Param("articleId") int articleId);
    List<Map<String, Object>> selectArticlePage(@Param("start") int start, @Param("len") int len,@Param("userId") int userId);
    int updateArticleCommentNum(@Param("add") int add,@Param("articleId") int articleId);
    Map<String, Object> selectRecomArticle(@Param("id")String id);
    String selectUserIdByArticleId(@Param("id")String id);
}
