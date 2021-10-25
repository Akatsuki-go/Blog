package com.chen.controller;

import com.chen.entity.Page;
import com.chen.entity.Picture;
import com.chen.service.serviceInterface.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/picture")
public class PictureController {
    @Autowired
    PictureService pictureService;

    //    查询用户最近添加的照片
    @PostMapping("/pLately")
    public List<Picture> queryLatelyPictures(@RequestBody Map<String, Object> map) {
//        return photoService.listLatelyPicture(map);
        return pictureService.listLatelyPicture(map);
    }

    /*
    1.保存图片
    2.数据保存到数据库
     */
//    @PostMapping("/add")
//    public String addPicture(@) {
//    }

    //单图片上传
    @PostMapping("/up")
    public String upPicture(Picture picture, @RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
//        System.out.println(file);
        return pictureService.savePicture(picture, file,"D:/Picture");
//        return " ";
    }
    //多图片上传

    //删除一张照片
    @GetMapping("/delete/{id}")
    public String deletePicture(@PathVariable("id") int id) {
        if (pictureService.removePicture(id) > 0)
            return "success";
        else
            return "error";
    }

    //查询一张
    @GetMapping("/query/{id}")
    public String queryPicture(@PathVariable("id") int id) {
        if (pictureService.getPicture(id)!=null) {
            return "success";
        } else {
            return "error";
        }
    }

    //改变一张照片的名字
    @PostMapping("/updatePicName")
    /*
    Map-key
    picId,picName
     */
    public String updatePicName(@RequestBody HashMap<String, Object> map) {
        int i = pictureService.updatePictureName(map);
        if (i > 0) {
            return "success";
        }
        if (i == 0) {
            return "no change";
        }

        return "error";
    }

    @GetMapping("/page/{albumId}/{pageNo}/{pageSize}")
    public Page queryAlbumPicturePage (@PathVariable int albumId,@PathVariable int pageNo,@PathVariable int pageSize){
        return pictureService.listAlbumPicture(albumId, pageNo, pageSize);

    }
}
