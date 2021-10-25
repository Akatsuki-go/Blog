package com.chen.service.serviceInterface;

import com.chen.entity.Album;
import com.chen.entity.Page;

import java.util.HashMap;


public interface AlbumService {
    //·某图片的评论page limit pictureId
    Page ListAlbumPage(int userId,int pageNo,int pageSize);
    //增删
    int saveAlbum(Album album);
    int deleteAlbum(int id);
    //修改相册名
    int updateAlbumName(HashMap<String,Object> map);

}
