package com.chen.dao;

import com.chen.entity.Comment;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ArticleCommentMapper {
    int insertArticleComment(Comment comment);
    int deleteArticleComment(int id);
    int updateArticleComment(Comment comment);
    List<Comment> selectArticleCommentList();
    Comment selectArticleCommentByArticleId();

    List<Map<String, Object>> selectCommentTopOrderByNewTimeLimit(@Param("start") int start, @Param("len") int len,@Param("userId")String userId);
    Map<String, Object> selectTopCommentById(@Param("id") String id);
    int countArticleComment();
    int countArticleCommentByArticleId(int articleId);

    //外表id查相关的评论记录
    List<Map<String, Object>> selectArticleCommentPageByArticleId(@Param("articleId") int articleId,@Param("start") int start, @Param("len") int len);

    //用户的最近文章评论
    @MapKey("id")
    List<Map<String, Object>> selectArticleCommentsLatelyByUserId(@Param("userId")String userId,@Param("start")int start,@Param("len")int len);
    //用户文章评论总数
    int countAriComNum(String userId);
}
