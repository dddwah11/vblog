package com.xiaoliu.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoliu.system.common.config.RedisCache;
import com.xiaoliu.system.common.constants.Constants;
import com.xiaoliu.system.common.handler.BusinessException;
import com.xiaoliu.system.common.response.Result;
import com.xiaoliu.system.common.response.ResultCode;
import com.xiaoliu.system.entity.Link;
import com.xiaoliu.system.service.ILinkService;
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
 * @since 2022-08-26
 */
@CrossOrigin
@RestController
@RequestMapping("/link")
public class LinkController {

    @Resource
    private ILinkService linkService;

    @Resource
    private RedisCache redisCache;

    /**
     * 查询友链
     * @return
     */
    @GetMapping("/list")
    public Result list(){
        List<Link> records = redisCache.getCacheList(Constants.LIST_LINK);
        if (records.size()==0) {
            records = linkService.list();
            redisCache.setCacheList(Constants.LIST_LINK, records);
            redisCache.expire(Constants.LIST_LINK, 1, TimeUnit.HOURS);
        }
        return Result.ok().data("records", records);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @PostMapping("/del/{id}")
    public Result del(@PathVariable Integer id){
        linkService.removeById(id);
        redisCache.delCacheMap(Constants.LIST_LINK);
        return Result.ok();
    }

    /**
     * 新增
     * @param link
     * @return
     */
    @PostMapping("/reg")
    public Result reg(@RequestBody Link link){
        linkService.saveOrUpdate(link);
        redisCache.delCacheMap(Constants.LIST_LINK);
        return Result.ok();
    }

}

