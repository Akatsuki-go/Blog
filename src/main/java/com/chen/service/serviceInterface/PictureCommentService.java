package com.chen.service.serviceInterface;

import com.chen.entity.Comment;
import com.chen.entity.CommentPage;
import com.chen.entity.Page;

import java.util.List;
import java.util.Map;

public interface PictureCommentService {
    //    用户最近关于图片的评论
    List<Comment> listLatelyComment(Map<String, Object> map);

    //isud
    int savePicComment(Comment comment);

    int removePicComment(int id);

    Comment getPicComment(int id);


    //某用户的评论分页
    Page listPicCommentPageByUserId(int userId,int pageNo,int pageSize);
    //某图片的评论分页
    CommentPage listPicCommentPageByPicId(String userId, int picId, int pageNo, int pageSize);

}
