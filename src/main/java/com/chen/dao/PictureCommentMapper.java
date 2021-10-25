package com.chen.dao;

import com.chen.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PictureCommentMapper {
//    评论页
    List<Map<String, Object>> selectPictureCommentPage(@Param("pictureId")int pictureId,@Param("start") int start,@Param("len")int len);

//    增删查 评论
    int insertPictureComment(Comment comment);
    int deletePictureComment(int id);
    Comment selectPictureCommentById(int id);

//    用户的最近的图片评论
    List<Map<String, Object>> selectCommentLatelyByUserId(@Param("userId")int userId,@Param("start")int start,@Param("len")int len);
    //一张图片的总评论数
    int countComments(int pictureId);
    int countPicComNum(int userId);
}
