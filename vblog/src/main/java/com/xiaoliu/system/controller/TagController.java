package com.xiaoliu.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoliu.system.common.config.RedisCache;
import com.xiaoliu.system.common.constants.Constants;
import com.xiaoliu.system.common.handler.BusinessException;
import com.xiaoliu.system.common.response.Result;
import com.xiaoliu.system.common.response.ResultCode;
import com.xiaoliu.system.entity.Tag;
import com.xiaoliu.system.service.ITagService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xiaoliu
 * @since 2022-08-24
 */
@CrossOrigin
@RestController
@RequestMapping("/tag")
public class TagController {
    @Resource
    private RedisCache redisCache;

    @Resource
    private ITagService tagService;


    /**
     * 查询全部
     * @return
     */
    @GetMapping("/all")
    public Result getAll(){
        Integer total = redisCache.getCacheObject(Constants.TAG_TOTAL);
        if (total==null) {
            Page<Tag> page = new Page<>();
            Page<Tag> tagPage = tagService.page(page);
            long total1 = tagPage.getTotal();
            total = (int) total1;
            redisCache.setCacheObject(Constants.TAG_TOTAL, total);
            redisCache.expire(Constants.TAG_TOTAL, 1, TimeUnit.HOURS);
        }
        return Result.ok().data("total", total);

    }

    /**
     * 查找类别表
     * @return
     */
    @GetMapping("/list")
    public Result getList(){
        List<Tag> tag = redisCache.getCacheList(Constants.LIST_TAG);
        if (tag.size()==0) {
            tag = tagService.findTag();
            redisCache.setCacheList(Constants.LIST_TAG, tag);
            redisCache.expire(Constants.LIST_TAG, 1, TimeUnit.HOURS);
        }
        return Result.ok().data("records", tag);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @PostMapping("/del/{id}")
    public Result del(@PathVariable(value = "id") Integer id){

        tagService.removeById(id);
        redisCache.delCacheMap(Constants.TAG_TOTAL);
        redisCache.delCacheMap(Constants.LIST_TAG);
        return Result.ok();
    }

    /**
     * 添加
     * @param tag
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Tag tag){
        tagService.saveOrUpdate(tag);
        redisCache.delCacheMap(Constants.TAG_TOTAL);
        redisCache.delCacheMap(Constants.LIST_TAG);
        return Result.ok();
    }
}

