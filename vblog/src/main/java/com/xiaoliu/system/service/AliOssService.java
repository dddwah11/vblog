package com.xiaoliu.system.service;

import com.xiaoliu.system.common.response.Result;
import com.xiaoliu.system.entity.Photo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author: 61分
 * @date: 2022/8/26 20:55
 * @description:
 */
public interface AliOssService {

    //    创建储存空间
    void createBucket();

    //    上传文件
    String upload(MultipartFile file);


    //    下载文件
//    void download(String fileName, HttpServletResponse response) throws IOException;


    //    删除文件
    void deleteFile(String fileName);

//    上传照片墙图片
    String uploadPhoto(MultipartFile file);
}
