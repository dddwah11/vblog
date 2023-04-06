package com.xiaoliu.system.service.impl;

import com.xiaoliu.system.common.constants.Constants;
import com.xiaoliu.system.common.handler.BusinessException;
import com.xiaoliu.system.common.response.ResultCode;
import com.xiaoliu.system.common.utils.MinioUtil;
import com.xiaoliu.system.entity.Files;
import com.xiaoliu.system.entity.MinioEntity;
import com.xiaoliu.system.mapper.FileMapper;
import com.xiaoliu.system.service.MinioService;
import io.minio.MinioClient;
import org.checkerframework.checker.units.qual.A;
import org.joda.time.DateTime;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.UUID;

/**
 * @Author: 61分
 * @Date: 2022-11-04 12:06
 * @Description:
 */
@Service
public class MinioServiceImpl implements MinioService, InitializingBean {

    @Resource
    private FileMapper fileMapper;

    @Resource
    private MinioUtil minioUtil;
    @Resource
    private MinioEntity minio;
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;
    @Value("${myminio.anotherip}")
    private String anotherIp;

    @Override
    public void afterPropertiesSet(){
        endpoint = minio.getEndpoint();
        accessKey = minio.getAccessKey();
        secretKey = minio.getSecretKey();
        bucketName = minio.getBucketName();
    }


    @Override
    public String upload(MultipartFile file) {
        String uploadUrl =null;
        MinioClient minioClient = MinioClient.builder().endpoint(endpoint).credentials(accessKey,secretKey).build();
        try {
//            文件名
            String originalFilename = file.getOriginalFilename();
//            文件类型
            String type = originalFilename.substring(originalFilename.lastIndexOf("."));
//            获取文件输入流
            InputStream is = file.getInputStream();
//            根据时间创建文件路径
            String datePath = new DateTime().toString("yyyy/MM/dd");
//            创建uuid
            String uuid = UUID.randomUUID().toString().replace("-", "");
//            生成新的文件名
            String newName = uuid + type;
//            生成文件夹
            String fileFolder = datePath + "/" + newName;
            Boolean flag = minioUtil.upload(bucketName, fileFolder, file, is,minioClient);
            if (!flag) {
                throw new BusinessException(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg());
            }
            uploadUrl = getUrl(fileFolder);
            Files files = new Files();
            files.setUuid(uuid);
            files.setType(type);
            files.setUrl(uploadUrl);
            files.setName(originalFilename);
            files.setIsPhoto(Constants.NOPHOTO);
            files.setSize(file.getSize());
            fileMapper.insert(files);
        } catch (Exception e) {
           throw new BusinessException(ResultCode.ERROR.getCode(),ResultCode.ERROR.getMsg());
        }
        return uploadUrl;
    }

    @Override
    public void deleteFile(String fileName) {
        try {
            MinioClient minioClient = MinioClient.builder().endpoint(endpoint).credentials(accessKey,secretKey).build();
            minioUtil.remove(fileName, bucketName, minioClient);
        } catch (Exception e) {
            throw new BusinessException(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg());
        }

    }

    @Override
    public String uploadPhoto(MultipartFile file) {
        String uploadUrl =null;
        MinioClient minioClient = MinioClient.builder().endpoint(endpoint).credentials(accessKey,secretKey).build();
        try {
//            文件名
            String originalFilename = file.getOriginalFilename();
//            文件类型
            String type = originalFilename.substring(originalFilename.lastIndexOf("."));
//            获取文件输入流
            InputStream is = file.getInputStream();
//            根据时间创建文件路径
            String datePath = new DateTime().toString("yyyy/MM/dd");
//            创建uuid
            String uuid = UUID.randomUUID().toString().replace("-", "");
//            生成新的文件名
            String newName = uuid + type;
//            生成文件夹
            String fileFolder = datePath + "/" + newName;
            Boolean flag = minioUtil.upload(bucketName, fileFolder, file, is,minioClient);
            if (!flag) {
                throw new BusinessException(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg());
            }
            uploadUrl = getUrl(fileFolder);
            Files files = new Files();
            files.setUuid(uuid);
            files.setType(type);
            files.setUrl(uploadUrl);
            files.setName(originalFilename);
            files.setIsPhoto(Constants.PHOTO);
            files.setSize(file.getSize());
            fileMapper.insert(files);
        } catch (Exception e) {
            throw new BusinessException(ResultCode.ERROR.getCode(),ResultCode.ERROR.getMsg());
        }
        return uploadUrl;
    }

    private String getUrl(String newFile) {
        return    anotherIp
                + "/"
                + bucketName
                + "/"
                + newFile;
    }


}
