package com.xiaoliu.system.common.utils;

import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件服务器工具类
 */
@Component
public class MinioUtil {

    /**
     * 查看存储bucket是否存在
     * @return boolean
     */
    public Boolean bucketExists(MinioClient minioClient,String bucketName) {
        Boolean found;
        try {
            found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            return false;
        }
        return found;
    }

    /**
     * 创建存储bucket
     * @return Boolean
     */
    public Boolean makeBucket(String bucketName,MinioClient minioClient) {
        try {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 删除存储bucket
     * @return Boolean
     */
    public Boolean removeBucket(String bucketName,MinioClient minioClient) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取全部bucket
     */
    public List<Bucket> getAllBuckets(MinioClient minioClient) {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 文件上传
     * @param file 文件
     * @return Boolean
     */
    public Boolean upload(String bucketName, String fileName, MultipartFile file, InputStream inputStream,MinioClient minioClient) {
        try {
            PutObjectArgs objectArgs = PutObjectArgs.builder().bucket(bucketName).object(fileName)
                    .stream(inputStream,file.getSize(),-1).contentType(file.getContentType()).build();
            //文件名称相同会覆盖
            minioClient.putObject(objectArgs);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 预览图片
     * @param fileName
     * @return
     */
    public String preview(String fileName,String bucketName,MinioClient minioClient){
        // 查看文件地址
        GetPresignedObjectUrlArgs build = GetPresignedObjectUrlArgs
                .builder().bucket(bucketName)
                .object(fileName)
                .method(Method.GET).build();
        try {
            String url = minioClient.getPresignedObjectUrl(build);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 文件下载
     * @param fileName 文件名称
     * @param res response
     * @return Boolean
     */
    public void download(String fileName,String bucketName, HttpServletResponse res,MinioClient minioClient) {
        GetObjectArgs objectArgs = GetObjectArgs.builder().bucket(bucketName)
                .object(fileName).build();
        try (GetObjectResponse response = minioClient.getObject(objectArgs)){
            byte[] buf = new byte[1024];
            int len;
            try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()){
                while ((len=response.read(buf))!=-1){
                    os.write(buf,0,len);
                }
                os.flush();
                byte[] bytes = os.toByteArray();
                res.setCharacterEncoding("utf-8");
                //设置强制下载不打开
                //res.setContentType("application/force-download");
                res.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
                try (ServletOutputStream stream = res.getOutputStream()){
                    stream.write(bytes);
                    stream.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查看文件对象
     * @return 存储bucket内文件对象信息
     */
    public List<Item> listObjects(String bucketName,MinioClient minioClient) {
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(bucketName).build());
        List<Item> items = new ArrayList<>();
        try {
            for (Result<Item> result : results) {
                items.add(result.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return items;
    }

    /**
     * 删除
     * @param fileName
     * @return
     * @throws Exception
     */
    public boolean remove(String fileName,String bucketName,MinioClient minioClient){
        try {
            minioClient.removeObject( RemoveObjectArgs.builder().bucket(bucketName).object(fileName).build());
        }catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     * 批量删除文件对象（没测试）
     * @param objects 对象名称集合
     */
    public Iterable<Result<DeleteError>> removeObjects(List<String> objects, String bucketName,MinioClient minioClient) {
        List<DeleteObject> dos = objects.stream().map(e -> new DeleteObject(e)).collect(Collectors.toList());
        Iterable<Result<DeleteError>> results = minioClient.removeObjects(RemoveObjectsArgs.builder().bucket(bucketName).objects(dos).build());
        return results;
    }
}

