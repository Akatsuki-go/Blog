package com.chen.controller;

import com.chen.entity.Album;
import com.chen.entity.Page;
import com.chen.service.serviceInterface.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    AlbumService albumService;
//·某图片的评论page limit pictureId

    //相册分页
    @GetMapping("/page/{userId}/{pageNo}/{pageSize}")
    public Page queryAlbumPage(@PathVariable("userId") int userId, @PathVariable("pageNo") int pageNo, @PathVariable("pageSize") int pageSize) {
        return albumService.ListAlbumPage(userId, pageNo, pageSize);
    }

    @PostMapping("/add")
    public String addAlbum(@RequestBody Album album) {
        if (albumService.saveAlbum(album) > 0) {
            return "success";
        } else {
            return "error";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteAlbum(@PathVariable("id") int id) {
        if (albumService.deleteAlbum(id) > 0) {
            return "success";
        } else {
            return "error";
        }
    }

    @PostMapping("/updateAlbName")
    public String updateAlbName(@RequestBody HashMap<String, Object> map) {
        if (albumService.updateAlbumName(map)>0){
            return "success";
        }else{
            return "error";
        }
    }
    
}
