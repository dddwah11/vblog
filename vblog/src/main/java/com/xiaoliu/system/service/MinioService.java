package com.xiaoliu.system.service;

import org.springframework.web.multipart.MultipartFile;

public interface MinioService {

    //    上传文件
    String upload(MultipartFile file);

    //    删除文件
    void deleteFile(String fileName);

    //    上传照片墙图片
    String uploadPhoto(MultipartFile file);
}
