package com.chen.dao;

import com.chen.entity.Picture;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface PictureMapper {
    //图片页
    List<Picture> selectPicturePage(@Param("groupId") int groupId,@Param("start") int start,@Param("len") int len);
    Picture selectOnePicture(BigInteger groupId);

    //增删改
    int insertPicture(Picture picture);
    int deletePicture(int id);
    int updatePictureName(@Param("pictureId") int pictureId,@Param("pictureName")String pictureName);
    Picture selectPictureById(int id);
    //    用户的最近添加的图片
    List<Picture> selectPictureListLately(@Param("userId")int userId,@Param("start")int start,@Param("len")int len);
    //根据相册id查所有图片
    List<Picture> selectPictureListByGourpId(@Param("gourpId") int gourpId,@Param("start")int start,@Param("len")int len);
    //相册图片数
    int countPictureNumByGourpId(int gourpId);

}