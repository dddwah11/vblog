package com.xiaoliu.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoliu.system.common.config.RedisCache;
import com.xiaoliu.system.common.handler.BusinessException;
import com.xiaoliu.system.common.response.Result;
import com.xiaoliu.system.common.response.ResultCode;
import com.xiaoliu.system.entity.Source;
import com.xiaoliu.system.service.ISourceService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.xiaoliu.system.common.constants.Constants.SOURCE_LIST;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xiaoliu
 * @since 2022-11-05
 */
@RestController
@RequestMapping("/source")
public class SourceController {

    @Autowired
    private ISourceService sourceService;
    @Autowired
    private RedisCache redisCache;

    @PostMapping("/reg")
    public Result reg(@RequestBody Source source) {
        try {
            sourceService.save(source);
            redisCache.delCacheMap(SOURCE_LIST);
            return Result.ok();
        } catch (Exception e) {
            throw new BusinessException(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg());
        }
    }


    @PostMapping("/del/{id}")
    public Result reg(@PathVariable("id") Integer id) {
        try {
            sourceService.removeById(id);
            redisCache.delCacheMap(SOURCE_LIST);
            return Result.ok();
        } catch (Exception e) {
            throw new BusinessException(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg());
        }
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody Source source) {
        try {
            sourceService.updateById(source);
            redisCache.delCacheMap(SOURCE_LIST);
            return Result.ok();
        } catch (Exception e) {
            throw new BusinessException(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg());
        }
    }

    @GetMapping("/listAll")
    public Result listAll() {
        List<Source> list = redisCache.getCacheList(SOURCE_LIST);
        if (list==null || list.size()==0) {
            try {
                 list = sourceService.listAll();
                System.out.println(list);
                redisCache.setCacheList(SOURCE_LIST, list);
                redisCache.expire(SOURCE_LIST, 1, TimeUnit.HOURS);
                return Result.ok().data("records", list);
            } catch (Exception e) {
                throw new BusinessException(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg());
            }
        } else {
            return Result.ok().data("records", list);
        }
    }

}

