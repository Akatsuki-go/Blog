package com.chen.utils;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.util.UUID;

public class FileUtils {
    //uuid生成随机文件名
    public static  String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static  String getSuffix(MultipartFile file){
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
        return suffix;
    }
    public static  String getSuffix(String fileName){
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        return suffix;
    }

    //CommonsMultipartFile文件存储到服务器
    public static Boolean saveFile(MultipartFile file,File realPath,String uploadFileName)throws IOException{
        try {
            InputStream is = file.getInputStream();
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

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
}
