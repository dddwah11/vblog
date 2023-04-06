package com.xiaoliu.system.controller;


import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoliu.system.common.config.RedisCache;
import com.xiaoliu.system.common.constants.Constants;
import com.xiaoliu.system.common.handler.BusinessException;
import com.xiaoliu.system.common.response.Result;
import com.xiaoliu.system.common.response.ResultCode;
import com.xiaoliu.system.entity.Category;
import com.xiaoliu.system.service.ICategoryService;
//import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
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
 * @since 2022-08-15
 */
@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @Resource
    private RedisCache redisCache;

    /**
     * 获取总数表
     * @return
     */
    @GetMapping("/total")
    public Result total(){
        Integer total = redisCache.getCacheObject(Constants.CATEGORYTOTAL);
        if (null ==total) {
            try {
                Page<Category> page1 = categoryService.page(new Page<>());
                long total1 = page1.getTotal();
                total = (int) total1;
                redisCache.setCacheObject(Constants.CATEGORYTOTAL, total, 1, TimeUnit.HOURS);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return Result.ok().data("total", total);

    }

    /**
     * 查找标签使用数
     * @return
     */
    @GetMapping("/count")
    public Result search(){
        List<Category> searchCount = redisCache.getCacheList(Constants.TAG_USER_COUNT);
        if (searchCount.size()==0) {
            searchCount = categoryService.searchCount();
            redisCache.setCacheList(Constants.TAG_USER_COUNT, searchCount);
            redisCache.expire(Constants.TAG_USER_COUNT, 1, TimeUnit.HOURS);
        }
        return Result.ok().data("records",searchCount);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @PostMapping("/del/{id}")
    public Result del(@PathVariable(value = "id") Integer id){
        categoryService.removeById(id);
        redisCache.delCacheMap(Constants.CATEGORYTOTAL);
        redisCache.delCacheMap(Constants.TAG_USER_COUNT);
        return Result.ok();
    }

    /**
     * 新增
     * @param category
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Category category){
        categoryService.saveOrUpdate(category);
        redisCache.delCacheMap(Constants.CATEGORYTOTAL);
        redisCache.delCacheMap(Constants.TAG_USER_COUNT);
        return Result.ok();
    }
}

