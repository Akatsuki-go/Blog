package com.chen.service;

import com.chen.dao.AlbumMapper;
import com.chen.dao.PictureMapper;
import com.chen.entity.Album;
import com.chen.entity.Page;
import com.chen.entity.Picture;
import com.chen.entity.Url;
import com.chen.service.serviceInterface.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    AlbumMapper albumMapper;
    @Autowired
    PictureMapper pictureMapper;


    @Override
    public Page ListAlbumPage(int userId,int pageNo,int pageSize) {
        int start=(pageNo-1)*pageSize;
        List<Map<String, Object>> list = albumMapper.selectAlbumPage(userId, start, pageSize);
//        System.out.println("list:"+list);


        for (Map<String, Object> map : list) {
            Picture p = pictureMapper.selectOnePicture((BigInteger)map.get("id"));
            String src="";
            if (p!=null)
                src= Url.getUrl() +"static/picture/"+p.getRealName();

//            System.out.println("src==="+src);
            map.put("src",src);
        }
//        System.out.println(list);
        Page page = new Page();
//        System.out.println(pageNo);

        page.setPageSize(pageSize);
        page.setItems(list);
        int count = albumMapper.countAlbumNum();
        page.setRecordCount(count);
        page.setPageTotal((count+pageSize-1)/pageSize);

        page.setPageNo(pageNo);
        return page;
    }

    @Override
    public int saveAlbum(Album album) {
        System.out.println("album:"+album);
        return albumMapper.insertAlbum(album);
    }


    @Override
    public int deleteAlbum(int id) {
        return albumMapper.deleteAlbum(id);
    }

    @Override
    public int updateAlbumName(HashMap<String, Object> map) {
//        System.out.println("map="+map);

        String groupName=(String)map.get("albumName");
        int id=(Integer)map.get("albumId");
        return albumMapper.updateAlbumName(groupName,id);
    }
}
