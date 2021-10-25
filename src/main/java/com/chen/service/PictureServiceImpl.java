package com.chen.service;

import com.chen.dao.AlbumMapper;
import com.chen.dao.PictureMapper;
import com.chen.entity.Page;
import com.chen.entity.Picture;
import com.chen.entity.Url;
import com.chen.service.serviceInterface.PictureService;
import com.chen.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PictureServiceImpl implements PictureService {
    @Autowired
    AlbumMapper albumMapper;
    @Autowired
    PictureMapper pictureMapper;


    @Transactional
    @Override
    /*
    1.图片存服务器
    2.路径写入数据库
    3.事务：相册照片数量
     */
    public String savePicture(Picture picture, MultipartFile file, String path) {
        if (file == null) {
            return "emtryFile";
        } else {//上传路径保存设置
            File realPath = new File(path);
            if (!realPath.exists()) {
                realPath.mkdir();
            }
            System.out.println("realPath:"+realPath);
            //路径+文件名
            String suffix = FileUtils.getSuffix(file);
            String uploadFileName = FileUtils.getUUID();
            uploadFileName +="."+ suffix;

            picture.setSuffix(suffix);

            picture.setRealName(uploadFileName);
            System.out.println("uploadFileName:"+uploadFileName);
//            写入
            try {
                if (!FileUtils.saveFile(file, realPath, uploadFileName)) {
                    return "writer fail";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        albumMapper.updatePhotoNum(picture.getGroupId(), 1);
        pictureMapper.insertPicture(picture);

        return "success";
    }

    @Override
    public Picture getPicture(int id) {
        return pictureMapper.selectPictureById(id);
    }

    @Override
    public int removePicture(int id) {
        return pictureMapper.deletePicture(id);
    }

    @Override
    public int updatePictureName(HashMap<String, Object> map) {
        Integer picId = (Integer) map.get("picId");
        String picName= (String) map.get("name");
        return pictureMapper.updatePictureName(picId,picName);
    }

    @Override
    public List<Picture> listLatelyPicture(Map<String, Object> map) {
        Integer userId = (Integer) map.get("userId");
        Integer start= (Integer) map.get("start");
        Integer len= (Integer) map.get("len");
        return pictureMapper.selectPictureListLately(userId,start,len);
    }

    @Override
    public Page listAlbumPicture(int gourpId, int PageNo, int PageSize) {
        int start=(PageNo-1)*PageSize;
        int end=start+PageSize;
        int count = pictureMapper.countPictureNumByGourpId(gourpId);

        List<Map<String, Object>> list=new ArrayList<>();
        List<Picture> pictures = pictureMapper.selectPictureListByGourpId(gourpId,start,PageSize);
        for (Picture picture : pictures) {
            String src= Url.getUrl() +"static/picture/"+picture.getRealName();
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("picture", picture);
            map.put("src", src);
            list.add(map);
        }
        Page page = new Page(PageSize,count, start, end, list,PageNo);

//        System.out.println(page);
        return page;
    }
}
