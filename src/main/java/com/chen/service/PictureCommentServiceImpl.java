package com.chen.service;

import com.chen.dao.PictureCommentMapper;
import com.chen.entity.Comment;
import com.chen.entity.CommentPage;
import com.chen.entity.Page;
import com.chen.service.serviceInterface.PictureCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PictureCommentServiceImpl implements PictureCommentService {
    @Autowired
    PictureCommentMapper pictureCommentMapper;
    @Autowired
    RedisServiceImpl redisService;

    @Override
    public List<Comment> listLatelyComment(Map<String, Object> map) {
//        Integer userId = (Integer) map.get("userId");
//        Integer start= (Integer) map.get("start");
//        Integer len= (Integer) map.get("len");
//        return pictureCommentMapper.selectCommentLatelyByUserId(userId,start,len);
        return null;
    }

    @Override
    public int savePicComment(Comment comment) {
        return pictureCommentMapper.insertPictureComment(comment);
    }

    @Override
    public int removePicComment(int id) {
        return pictureCommentMapper.deletePictureComment(id);
    }

    @Override
    public Comment getPicComment(int id) {
        return pictureCommentMapper.selectPictureCommentById(id);
    }




    @Override
    public CommentPage listPicCommentPageByPicId(String userId, int pictureId, int pageNo, int pageSize) {
        int start=(pageNo-1)*pageSize;
        int end=start+pageSize;
        int count = pictureCommentMapper.countComments(pictureId);
        List<Map<String, Object>> list = pictureCommentMapper.selectPictureCommentPage(pictureId,start,pageSize);
        List<String> idList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            idList.add(String.valueOf(map.get("id")));
        }
        List<Boolean> isGoodlist = redisService.getComGoodPage(idList, userId, "picCom");
        List<Integer> goodList=redisService.getPageComGoodNum(idList,"picCom");
        CommentPage page = new CommentPage(pageSize,count,start,end,list,pageNo,isGoodlist,goodList);

        return page;
    }

    @Override
    public Page listPicCommentPageByUserId(int UserId, int pageNo, int pageSize) {
        int count=pictureCommentMapper.countPicComNum(UserId);
        int start=(pageNo-1)*pageSize;
        int end=start+pageSize;
        List<Map<String, Object>> list = pictureCommentMapper.selectCommentLatelyByUserId(UserId, start, pageSize);
        Page page = new Page(pageSize, count, start, end, list, pageNo);

        System.out.println("picComPage: "+page);
        return page;
    }
}
