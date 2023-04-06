package com.xiaoliu.system.controller;

import com.xiaoliu.system.common.config.RedisCache;
import com.xiaoliu.system.common.constants.Constants;
import com.xiaoliu.system.common.handler.BusinessException;
import com.xiaoliu.system.common.response.Result;
import com.xiaoliu.system.common.response.ResultCode;
import com.xiaoliu.system.service.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Author: 61分
 * @Date: 2022-11-04 14:40
 * @Description:
 */
@CrossOrigin
@RestController
@RequestMapping("/minio")
public class MinioController {
    @Resource
    private MinioService minioService;

    @Resource
    private RedisCache redisCache;

    /**
     * 上传文件
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam(value = "file") MultipartFile file
    )   {
        String upload = null;
        try {
            upload = minioService.upload(file);
        } catch (Exception e) {
            throw new BusinessException(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg());
        }
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
        String upload = null;
        try {
            upload = minioService.uploadPhoto(file);
        } catch (Exception e) {
            throw new BusinessException(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg());
        }
        redisCache.delCacheMap(Constants.LIST_PHOTO);
        return Result.ok().data("url",upload);

    }


}
