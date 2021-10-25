package com.chen.dao;

import com.chen.entity.Album;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AlbumMapper {
    List<Map<String, Object>> selectAlbumPage(@Param("userId")int userId,@Param("start")int start,@Param("len") int len);

    //增删查 相册 改相册名
    int insertAlbum(Album album);
    int deleteAlbum(int id);
    int updateAlbumName(@Param("groupName") String groupName,@Param("id")int albumId);

    int updatePhotoNum(@Param("groupId") int groupId,@Param("n")int n);
    int countAlbumNum();
}