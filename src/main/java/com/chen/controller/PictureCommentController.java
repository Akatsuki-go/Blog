package com.chen.controller;

import com.chen.entity.Comment;
import com.chen.entity.CommentPage;
import com.chen.entity.Page;
import com.chen.service.RedisServiceImpl;
import com.chen.service.serviceInterface.PictureCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/picCom")
public class PictureCommentController {
    @Autowired
    PictureCommentService pictureCommentService;
    @Autowired
    RedisServiceImpl redisService;

    @GetMapping("/lately/{userId}/{pageNo}/{pageSize}")
    public Page queryLatelyComments(@PathVariable("userId") int userId,@PathVariable("pageNo") int pageNo,@PathVariable("pageSize") int pageSize){
        return pictureCommentService.listPicCommentPageByUserId(userId, pageNo,pageSize);
    }
    //图片评论分页
    @GetMapping("/page/{userId}/{picId}/{pageNo}/{pageSize}")
    public CommentPage queryPicComPage (@PathVariable("userId") int userId,@PathVariable("picId") int picId,@PathVariable("pageNo") int pageNo,@PathVariable("pageSize") int pageSize){
        return pictureCommentService.listPicCommentPageByPicId(String.valueOf(userId),picId, pageNo, pageSize);
//        return null;
    }
    @PostMapping("/add")
    public int addPicComment (@RequestBody Comment comment){
        return pictureCommentService.savePicComment(comment);
    }
    @PostMapping("/upGood")
    public Map<String, Object> modifyPicComGoodNum(@RequestBody HashMap<String, String> map){
        Map<String, Object> picCom = redisService.upComGood(map, "picCom");
        System.out.println(picCom);
        return picCom;
    }
}
