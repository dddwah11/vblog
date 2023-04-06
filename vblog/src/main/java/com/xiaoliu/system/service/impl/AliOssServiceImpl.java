package com.xiaoliu.system.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.xiaoliu.system.common.constants.Constants;
import com.xiaoliu.system.entity.Files;
import com.xiaoliu.system.entity.OssEntity;
import com.xiaoliu.system.entity.Photo;
import com.xiaoliu.system.mapper.FileMapper;
import com.xiaoliu.system.mapper.PhotoMapper;
import com.xiaoliu.system.service.AliOssService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;

/**在创建这个属性值的时候就应该吧属性值转载好
 * @author: 61分
 * @date: 2022/8/26 20:55
 * @description:
 */
@Service
public class AliOssServiceImpl implements AliOssService,InitializingBean {

    @Resource
    private FileMapper fileMapper;


    @Autowired
    private OssEntity ossEntity;

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    /**
     * 初始化bean之后需要进行操作
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet()  {
        endpoint = ossEntity.getEndpoint();
        accessKeyId = ossEntity.getAccessKeyId();
        accessKeySecret = ossEntity.getAccessKeySecret();
        bucketName = ossEntity.getBucketName();

    }


    /**
     * 创建储存空间
     */
    @Override
    public void createBucket() {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        System.out.println("创建bucket");
        // 创建CreateBucketRequest对象。
        if (ossClient.doesBucketExist(bucketName)){
            throw new RuntimeException(bucketName + "此bucket列表已存在");
        }
            CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
            // 创建OSSClient实例。
            // 创建存储空间。
        ossClient.createBucket(createBucketRequest);
                ossClient.shutdown();
    }

    /**
     * 上传文件
     * @param
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String upload(MultipartFile file)  {
//        上传地址
        String uoloadUrl = null;

        try {
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            if (!ossClient.doesBucketExist(bucketName)){

                ossClient.createBucket(bucketName);
//                设置bucket属性
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            }
//            获取上传文件的流
            InputStream inputStream = file.getInputStream();

//            创建唯一标识
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//            方便管理 yyyy/MM/dd+uuid,构建日期路径
            String datePath = new DateTime().toString("yyyy/MM/dd");
//            获取文件全名称
            String originalFilename = file.getOriginalFilename();

//            获取上传文件的扩展名
            String type = originalFilename.substring(originalFilename.lastIndexOf("."));

//            拼接文件名称
            String newName = uuid + type;

//            生成文件夹
           String fileName = datePath + "/" + newName;

//            图片预览，一定要设置以下几点
//            1.设置文件的ACL（权限） 要么是公共读，要么是公共读写
//            2.一定要设置文本类型
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setObjectAcl(CannedAccessControlList.PublicRead);
            metadata.setContentType(getContentType(type));
            ossClient.putObject(bucketName, fileName, inputStream,metadata);
            ossClient.shutdown();

//            默认10年不过期
            Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);

//            获取url
            uoloadUrl = ossClient.generatePresignedUrl(bucketName, fileName, expiration).toString();

            Files files = new Files();
            long size = file.getSize();
            files.setType(type);
            files.setUrl(uoloadUrl.substring(0,uoloadUrl.indexOf("?")));
            files.setSize(size);
            files.setUuid(uuid);
            files.setName(originalFilename);
            files.setIsPhoto(Constants.NOPHOTO);
            fileMapper.insert(files);

        }catch (Exception e){
            e.printStackTrace();
        }


        return uoloadUrl.substring(0,uoloadUrl.indexOf("?"));
    }

//    /**
//     * 下载文件
//     * @param fileName
//     * @throws IOException
//     */
//    @Override
//    public void download(String fileName, HttpServletResponse response) throws IOException {
//        int index = fileName.indexOf("/");
//        int index1 = fileName.indexOf("/", index + 2);
//        int index2 = fileName.indexOf("/", index1 + 1);
//        String substring = fileName.substring(index2+1);
//
//
//            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(substring, "UTF-8"));
//        response.setContentType("application/octet-stream");
//
//        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//        OSSObject object = ossClient.getObject(bucketName, fileName);
//
//        try {
//            // 读取文件内容。
//            InputStream inputStream = object.getObjectContent();
//            BufferedInputStream in = new BufferedInputStream(inputStream);// 把输入流放入缓存流
//            ServletOutputStream outputStream = response.getOutputStream();
//            BufferedOutputStream out = new BufferedOutputStream(outputStream);// 把输出流放入缓存流
//            byte[] buffer = new byte[1024];
//            int len = 0;
//            while ((len = in.read(buffer)) != -1) {
//                out.write(buffer, 0, len);
//            }
//            if (out != null) {
//                out.flush();
//                out.close();
//            }
//            if (in != null) {
//                in.close();
//            }
//        } catch (Exception e) {
//        }
//    }




    /**
     * 删除文件
     * @param fileName
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteFile(String fileName) {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            ossClient.deleteObject(bucketName, fileName);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
        public String uploadPhoto(MultipartFile file) {
        //        上传地址
        String uoloadUrl = null;

        try {
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            if (!ossClient.doesBucketExist(bucketName)){

                ossClient.createBucket(bucketName);
//                设置bucket属性
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            }
//            获取上传文件的流
            InputStream inputStream = file.getInputStream();

//            创建唯一标识
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//            方便管理 yyyy/MM/dd+uuid,构建日期路径
            String datePath = new DateTime().toString("yyyy/MM/dd");
//            获取文件全名称
            String originalFilename = file.getOriginalFilename();

//            获取上传文件的扩展名
            String type = originalFilename.substring(originalFilename.lastIndexOf("."));

//            拼接文件名称
            String newName = uuid + type;

//            生成文件夹
            String fileName = datePath + "/" + newName;

//            图片预览，一定要设置以下几点
//            1.设置文件的ACL（权限） 要么是公共读，要么是公共读写
//            2.一定要设置文本类型
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setObjectAcl(CannedAccessControlList.PublicRead);
            metadata.setContentType(getContentType(type));
            ossClient.putObject(bucketName, fileName, inputStream,metadata);
            ossClient.shutdown();

//            默认10年不过期
            Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);

//            获取url
            uoloadUrl = ossClient.generatePresignedUrl(bucketName, fileName, expiration).toString();

            Files files = new Files();
            long size = file.getSize();
            files.setType(type);
            files.setUrl(uoloadUrl.substring(0,uoloadUrl.indexOf("?")));
            files.setSize(size);
            files.setUuid(uuid);
            files.setName(originalFilename);
            files.setIsPhoto(Constants.PHOTO);
            fileMapper.insert(files);

        }catch (Exception e){
            e.printStackTrace();
        }


        return uoloadUrl.substring(0,uoloadUrl.indexOf("?"));
    }

    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     *
     * @param fileName 文件名
     * @return 文件的contentType
     */
    public static final String getContentType(String fileName) {
        String FilenameExtension = fileName.substring(fileName.lastIndexOf("."));
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "application/x-bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xla") ||
                FilenameExtension.equalsIgnoreCase(".xlc")||
                FilenameExtension.equalsIgnoreCase(".xlm")||
                FilenameExtension.equalsIgnoreCase(".xls")||
                FilenameExtension.equalsIgnoreCase(".xlt")||
                FilenameExtension.equalsIgnoreCase(".xlw")) {
            return "application/vnd.ms-excel";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        if (FilenameExtension.equalsIgnoreCase(".pdf")) {
            return "application/pdf";
        }
        if (FilenameExtension.equalsIgnoreCase(".zip")) {
            return "application/zip";
        }
        if (FilenameExtension.equalsIgnoreCase(".tar")) {
            return "application/x-tar";
        }
        if (FilenameExtension.equalsIgnoreCase(".avi")) {
            return "video/avi";
        }
        if (FilenameExtension.equalsIgnoreCase(".mp4")) {
            return "video/mpeg4";
        }
        if (FilenameExtension.equalsIgnoreCase(".mp3")) {
            return "audio/mp3";
        }
        if (FilenameExtension.equalsIgnoreCase(".mp2")) {
            return "audio/mp2";
        }
        return "application/octet-stream";
    }

}
