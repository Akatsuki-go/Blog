package com.chen.service.serviceInterface;

import com.chen.entity.Comment;
import com.chen.entity.CommentPage;
import com.chen.entity.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ArticleCommentService {
   int addArticleComment(Comment comment);
   Page listArticleCommentsTopOrderByNewTimeLimit(int PageNo, int len,String userId);
   List<Comment> listArticleCommentsLately(Map<String,Object> map);

   CommentPage listArticleCommentPage(String userId, int articleId, int PageNo, int PageSize);

}
