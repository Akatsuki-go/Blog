package com.chen.service.serviceInterface;

import com.chen.entity.Page;
import com.chen.entity.Picture;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PictureService {
    //    增删查 图片 改图片名
    String savePicture(Picture picture, MultipartFile file, String path);
    int removePicture(int id);
    Picture getPicture(int id);
    int updatePictureName(HashMap<String, Object> map);
    //     用户最近图片
    List<Picture> listLatelyPicture(Map<String,Object> map);

    //某相册的照片分页
    Page listAlbumPicture(int gourpId,int PageNo,int PageSize);
}
