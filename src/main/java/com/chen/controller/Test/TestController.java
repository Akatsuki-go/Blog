package com.chen.controller.Test;


import com.chen.dao.ArticleMapper;
import com.chen.entity.Picture;
import com.chen.service.RedisServiceImpl;
import com.chen.service.serviceInterface.ArticleService;
import com.chen.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.chen.utils.RedisUtil;

@RestController
public class TestController {
    //    @Autowired
//    RedisTemplate redisTemplate;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    RedisServiceImpl redisService;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    ArticleService articleService;

    @PostMapping("/login")
    public String t1(@RequestBody Map<String, String> map) {
        String accountName = map.get("accountName");
        String password = map.get("password");

        return "";
    }

    @GetMapping("/redis")
    public String tt() {
        redisService.getRecommendArticle(0, 10);
        return "  ";
    }

    @PostMapping("/t3")
    public String t3(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) throws IOException {
        String result = "success";

        if (file == null) {
            result = "emtryFile";
        } else {//上传路径保存设置
            String path = request.getServletContext().getRealPath("/upload");//如果路径不存在，创建一个
            File realPath = new File(path);
            if (!realPath.exists()) {
                realPath.mkdir();
            }
            //保存
            System.out.println(realPath);

            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//            System.out.println(file.getOriginalFilename());


            String uploadFileName = UUID.randomUUID().toString().replace("-", "");
            uploadFileName += suffix;

            try (InputStream is = file.getInputStream()) {
                OutputStream os = new FileOutputStream(new File(realPath, uploadFileName)); //文件输出流
                //读取写出
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                    os.flush();
                }
                os.close();
                is.close();
            }
            //文件输入流
        }

        return result;
    }

    @GetMapping("/mapper")
    public String upListPicture() {
        System.out.println(articleMapper.selectRecomArticle("2"));
        return null;
    }
    @PostMapping("/recA")
    public List<Map<String, Object>> recA(@RequestBody Map<String, Object> map){
        return articleService.getRecommendArticle(map);
    }
}