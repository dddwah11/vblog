package com.xiaoliu.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoliu.system.common.config.RedisCache;
import com.xiaoliu.system.common.constants.Constants;
import com.xiaoliu.system.common.handler.BusinessException;
import com.xiaoliu.system.common.response.Result;
import com.xiaoliu.system.common.response.ResultCode;
import com.xiaoliu.system.entity.Files;
import com.xiaoliu.system.mapper.FileMapper;
import com.xiaoliu.system.service.AliOssService;
import com.xiaoliu.system.service.IFileService;
import com.xiaoliu.system.service.MinioService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xiaoliu
 * @since 2022-08-18
 */
@CrossOrigin
@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private AliOssService aliOssService;

    @Resource
    private FileMapper fileMapper;

    @Resource
    private RedisCache redisCache;

    @Resource
    private MinioService minioService;

    /**
     * 上传文件
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam(value = "file") MultipartFile  file) {
        String upload = aliOssService.upload(file);
        redisCache.delCacheMap(Constants.LIST_PHOTO);
        return Result.ok().data("url",upload);

    }

    /**
     * 上传图片
     * @param file
     * @return
     */
    @PostMapping("/uploadPhoto")
    public Result uploadPhoto(@RequestParam(value = "file") MultipartFile file
    )   {
        String upload = aliOssService.uploadPhoto(file);
        redisCache.delCacheMap(Constants.LIST_PHOTO);
        return Result.ok().data("url",upload);

    }

//    @GetMapping("/down")
//    public void download(@RequestParam(value = "fileUUID") String fileUUID,HttpServletResponse response) throws IOException {
//        fileUUID.replaceAll("%2F", "/");
//        String ossFileName = getOssFileName(fileUUID);
//        aliOssService.download(ossFileName, response);
//    }

    /**查询文件
     *
     * @return
     */
    @GetMapping("/list")
    public Result list(){
        List<Files> files = fileMapper.selectList(new LambdaQueryWrapper<Files>().orderByDesc(Files::getId).eq(Files::getIsPhoto, Constants.NOPHOTO));
        return Result.ok().data("records", files);
    }

    /**
     * 查询图片
     * @return
     */
    @GetMapping("/listPhoto")
    public Result listPhoto(){
        List<Files> records = redisCache.getCacheList(Constants.LIST_PHOTO);
        if (records.size()==0) {
             records = fileMapper.selectList(new QueryWrapper<Files>().orderByDesc("id").eq("is_photo", Constants.PHOTO));
            redisCache.setCacheList(Constants.LIST_PHOTO, records);
            redisCache.expire(Constants.LIST_PHOTO, 1, TimeUnit.HOURS);
        }
        return Result.ok().data("records", records);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Transactional
    @PostMapping("/del/{id}")
    public Result del(@PathVariable(value = "id") Integer id){
        try {
            Files files = fileMapper.selectById(id);
            String url = files.getUrl();
            String url2 = url.substring(url.indexOf("/")+2,url.indexOf("."));
            if ("images".equals(url2)) {
                String minioFileName = getMinioFileName(url);
                minioService.deleteFile(minioFileName);
            } else {
                String ossFileName = getOssFileName(url);
                aliOssService.deleteFile(ossFileName);
            }
        } catch (Exception e) {
            throw new BusinessException(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg());
        }
        fileMapper.deleteById(id);
        redisCache.delCacheMap(Constants.LIST_PHOTO);
        return Result.ok();
    }



    private String getOssFileName(String url) {
        int index = url.indexOf("/");
        index = url.indexOf("/", index + 2);
        String substring = url.substring(index+1);
        return substring;
    }

    private String getMinioFileName(String url) {
        int index = url.indexOf("/");
        index = url.indexOf("/", index + 2);
        String substring = url.substring(index+6);
        return substring;
    }
}

