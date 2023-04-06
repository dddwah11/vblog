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
import com.xiaoliu.system.entity.Photo;
import com.xiaoliu.system.mapper.PhotoMapper;
import com.xiaoliu.system.service.AliOssService;
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
 * @since 2022-08-29
 */
@CrossOrigin
@RestController
@RequestMapping("/photo")
public class PhotoController {


    @Resource
    private PhotoMapper photoMapper;

    /**
     * 上传照片游记事件
     * @param photo
     * @return
     */
    @PostMapping("/reg")
    public Result reg(@RequestBody Photo photo
    )  {
        photoMapper.insert(photo);
        return Result.ok();

    }

    /**
     * 编辑游记事件
     * @param photo
     * @return
     */
    @PostMapping("/edit")
    public Result edit(@RequestBody Photo photo
    ) {
        photoMapper.updateById(photo);
        return Result.ok();
    }


    /**
     * 查询事件列表
     * @return
     */
    @GetMapping("/list")
    public Result list(){
            Page<Photo> page = new Page<>();
            Page<Photo> filesPage = photoMapper.selectPage(page, new LambdaQueryWrapper<Photo>().orderByDesc(Photo::getId));
           List<Photo>  records = filesPage.getRecords();
        return Result.ok().data("records", records);
    }


    /**
     * 根据id删除
     * @param id
     * @return
     */
    @Transactional
    @PostMapping("/del/{id}")
    public Result del(@PathVariable(value = "id") Integer id){
        photoMapper.deleteById(id);
        return Result.ok();
    }


}

