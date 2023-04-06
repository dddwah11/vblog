package com.xiaoliu.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaoliu.system.common.config.RedisCache;
import com.xiaoliu.system.common.constants.Constants;
import com.xiaoliu.system.common.response.Result;
import com.xiaoliu.system.common.response.ResultCode;
import com.xiaoliu.system.entity.Blog;
import com.xiaoliu.system.entity.Category;
import com.xiaoliu.system.entity.Tag;
import com.xiaoliu.system.mapper.CategoryMapper;
import com.xiaoliu.system.mapper.TagMapper;
import com.xiaoliu.system.service.IBlogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xiaoliu
 * @since 2022-07-31
 */
@RestController
@RequestMapping("/blog")
@CrossOrigin
public class BlogController {
    @Resource
    private RedisCache redisCache;
    @Resource
    private IBlogService blogService;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private TagMapper tagMapper;

    /**
     * 归档页
     * @return
     */
    @GetMapping("/findAllBlog")
    public Result findAllBlog(){
        List<Blog> records = redisCache.getCacheList(Constants.BLOG_ARCHIVE_PAGE);
        Integer total = redisCache.getCacheObject(Constants.BLOG_TOTAL);
        if (records.size()==0 || total==null) {
            QueryWrapper<Blog> wrapper = new QueryWrapper<>();
            wrapper.orderByDesc("created_time");
             records = blogService.list(wrapper);
            Page<Blog> page = blogService.page(new Page<Blog>());
            total = (int) page.getTotal();
            redisCache.setCacheList(Constants.BLOG_ARCHIVE_PAGE, records);
                redisCache.setCacheObject(Constants.BLOG_TOTAL, total);
            redisCache.expire(Constants.BLOG_ARCHIVE_PAGE, 1, TimeUnit.HOURS);
            redisCache.expire(Constants.BLOG_TOTAL, 1, TimeUnit.HOURS);
        }
        return Result.ok().data("records", records).data("total",total);
    }

    /**首页文章
     * 分页
     * @param current
     * @param size
     * @return
     */
    @PostMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") int current,
                       @RequestParam(defaultValue = "6") int size) {
        List<Blog> records = redisCache.getCacheList(Constants.BLOG_ALL_PAGE);
        Integer total = redisCache.getCacheObject(Constants.BLOG_TOTAL);
        if (records.size()==0||total==null) {
             records = blogService.list(new QueryWrapper<Blog>()
                    .orderByDesc("weight")
                    .orderByDesc("created_time"));
            Assert.notNull(records,ResultCode.BLOG_EXCEPTION.getMsg(),ResultCode.BLOG_EXCEPTION.getCode());
            List<Integer> collect2 = records.stream().map(record -> record.getTagId()).collect(Collectors.toList());
            List<Tag> otag = tagMapper.selectBatchIds(collect2);
            for (Blog record : records) {
                List<Tag> categories3 = otag.stream().filter(c -> record.getTagId().equals(c.getId())).collect(Collectors.toList());
                for (Tag tag : categories3) {
                    record.setTagName(tag.getName());
                }
            }
            Page<Blog> page = blogService.page(new Page<>());

            total = (int) page.getTotal();
            redisCache.setCacheList(Constants.BLOG_ALL_PAGE, records);
            redisCache.setCacheObject(Constants.BLOG_TOTAL, total);
            redisCache.expire(Constants.BLOG_ALL_PAGE, 1, TimeUnit.HOURS);
            redisCache.expire(Constants.BLOG_TOTAL, 1, TimeUnit.HOURS);
        }
        if (records.size()!=0&&total!=null){
            records = redisCache.getCacheListPage(Constants.BLOG_ALL_PAGE, current, size, total);
            return Result.ok().data("records",records).data("total",total);
        }
        return Result.ok().data("records",records).data("total",total);
    }

    /**文章详情页
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result sear(@PathVariable(value = "id") Integer id){
        redisCache.incrementCacheMapValue(Constants.BLOGVIECOUT, id.toString(), 1);
        Object object = redisCache.getCacheMapValue(Constants.BLOG_ID,id.toString());
        Integer cacheMapValue = redisCache.getCacheMapValue(Constants.BLOGVIECOUT, id.toString());
        Blog blog = null;
        if (object!=null) {
            ObjectMapper mapper = new ObjectMapper();
            Blog blog1 = mapper.convertValue(object, Blog.class);
             blog = blog1;
            blog.setViewCounts(cacheMapValue);
        }
        if(object==null) {
            try {
//        从redis中获取viewCount
                Blog byId = blogService.getById(id);
                Category category = categoryMapper.selectById(byId.getCategoryId());
                String name = category.getName();
                byId.setCategoryName(name);
                byId.setViewCounts(cacheMapValue);
                blog =  byId;
                Map<String, Object> w = new HashMap<>();
                w.put(id.toString(), blog);
                redisCache.setCacheMap(Constants.BLOG_ID,w);
                redisCache.expire(Constants.BLOG_ID, 1, TimeUnit.HOURS);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return Result.ok().data("records", blog);
    }

    /**搜索功能
     * 分页模糊查询
     * @param current
     * @param size
     * @param blog
     * @return
     */
    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer current,
                       @RequestParam(defaultValue = "6") Integer size,
                       @RequestBody Blog blog) {
        IPage<Blog> blogPage =  blogService.listBlog(current, size,blog); //从数据库取出数据
        List<Blog> records = blogPage.getRecords();
        long total = blogPage.getTotal();
        return Result.ok().data("records",records).data("total",total);
    }





    /**
     * 新增
     * @param blog
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody Blog blog){
            blogService.saveBlog(blog);
            redisCache.delCacheMap(Constants.BLOG_TOTAL);
        redisCache.delCacheMap(Constants.BLOG_ALL_PAGE);
        redisCache.delCacheMap(Constants.BLOG_ARCHIVE_PAGE);
        redisCache.delCacheMap(Constants.BLOG_ID);
        redisCache.delCacheMap(Constants.BLOG_SEARCH_BY_CATEGORY_TOTAL);
        redisCache.delCacheByPrefix(Constants.BLOG_SEARCH_BY_CATEGORY_ID);
        redisCache.delCacheByPrefix(Constants.BLOG_SEARCH_BY_TAG_ID);
        redisCache.delCacheMap(Constants.BLOG_SEARCH_BY_CATEGORY_ID);
        redisCache.delCacheMap(Constants.CATEGORYTOTAL);
        redisCache.delCacheMap(Constants.TAG_USER_COUNT);
        redisCache.delCacheMap(Constants.TAG_TOTAL);
        redisCache.delCacheMap(Constants.LIST_TAG);
        return Result.ok();
    }

    /**
     * 编辑
     * @param blog
     * @return
     */
    @PostMapping("/edit")
    public Result edit(@RequestBody Blog blog){
        blogService.editBlog(blog);
        redisCache.delCacheMap(Constants.BLOG_TOTAL);
        redisCache.delCacheMap(Constants.BLOG_ALL_PAGE);
        redisCache.delCacheMap(Constants.BLOG_ARCHIVE_PAGE);
        redisCache.delCacheMap(Constants.BLOG_ID);
        redisCache.delCacheMap(Constants.BLOG_SEARCH_BY_CATEGORY_TOTAL);
        redisCache.delCacheByPrefix(Constants.BLOG_SEARCH_BY_CATEGORY_ID);
        redisCache.delCacheByPrefix(Constants.BLOG_SEARCH_BY_TAG_ID);
        redisCache.delCacheMap(Constants.BLOG_SEARCH_BY_CATEGORY_ID);
        redisCache.delCacheMap(Constants.CATEGORYTOTAL);
        redisCache.delCacheMap(Constants.TAG_USER_COUNT);
        redisCache.delCacheMap(Constants.TAG_TOTAL);
        redisCache.delCacheMap(Constants.LIST_TAG);
        return Result.ok();
    }


    /**
     * 删除
     * @param id
     * @return
     */
    @PostMapping("/del/{id}")
    public Result del(@PathVariable Integer id){
        blogService.del(id);
        redisCache.delCacheMap(Constants.BLOG_TOTAL);
        redisCache.delCacheMap(Constants.BLOG_ALL_PAGE);
        redisCache.delCacheMap(Constants.BLOG_ARCHIVE_PAGE);
        redisCache.delCacheMap(Constants.BLOG_ID);
        redisCache.delCacheMap(Constants.BLOG_SEARCH_BY_CATEGORY_TOTAL);
        redisCache.delCacheMap(Constants.BLOG_SEARCH_BY_TAG_TOTAL);
        redisCache.delCacheByPrefix(Constants.BLOG_SEARCH_BY_CATEGORY_ID);
        redisCache.delCacheByPrefix(Constants.BLOG_SEARCH_BY_TAG_ID);
        redisCache.delCacheMap(Constants.BLOG_SEARCH_BY_CATEGORY_ID);
        redisCache.delCacheMap(Constants.CATEGORYTOTAL);
        redisCache.delCacheMap(Constants.TAG_USER_COUNT);
        redisCache.delCacheMap(Constants.TAG_TOTAL);
        redisCache.delCacheMap(Constants.LIST_TAG);
        return Result.ok();
    }

    /**
     * 根据标签id查找博客表
     * @return
     */
    @GetMapping("/findByCa/{catagoryId}")
    public Result findByCa(@PathVariable(value = "catagoryId") Integer catagoryId
                           ){
        List<Blog> records = redisCache.getCacheList(Constants.BLOG_SEARCH_BY_CATEGORY_ID + catagoryId);
        Integer total = redisCache.getCacheObject(Constants.BLOG_SEARCH_BY_CATEGORY_TOTAL);
        if (records.size()==0||total==null) {
            QueryWrapper<Blog> wrapper = new QueryWrapper<>();
            wrapper.eq("category_id", catagoryId);
            records = blogService.list(wrapper);
            Assert.notNull(records,ResultCode.BLOG_EXCEPTION.getMsg(),ResultCode.BLOG_EXCEPTION.getCode());
            List<Integer> collect2 = records.stream().map(record -> record.getTagId()).collect(Collectors.toList());
            List<Tag> otag = tagMapper.selectBatchIds(collect2);
            for (Blog record : records) {
                List<Tag> categories3 = otag.stream().filter(c -> record.getTagId().equals(c.getId())).collect(Collectors.toList());
                for (Tag tag : categories3) {
                    record.setTagName(tag.getName());
                }
            }
            Page<Blog> page = blogService.page(new Page<>());
            total = (int) page.getTotal();
            redisCache.setCacheList(Constants.BLOG_SEARCH_BY_CATEGORY_ID + catagoryId, records);
            redisCache.setCacheObject(Constants.BLOG_SEARCH_BY_CATEGORY_TOTAL, total);
            redisCache.expire(Constants.BLOG_SEARCH_BY_CATEGORY_ID + catagoryId, 1, TimeUnit.HOURS);
            redisCache.expire(Constants.BLOG_SEARCH_BY_CATEGORY_TOTAL, 1, TimeUnit.HOURS);
        }
        if (records.size()!=0&&total!=null){
            records =redisCache.getCacheList(Constants.BLOG_SEARCH_BY_CATEGORY_ID + catagoryId);
            return Result.ok().data("records", records).data("total", total);
        }
        return Result.ok().data("records", records).data("total", total);
    }

    /**
     * 根据类别id查找博客表
     * @return
     */
    @GetMapping("/findByTag/{tagId}")
    public Result findByTag(@PathVariable(value = "tagId") Integer tagId
    ){
        List<Blog> records = redisCache.getCacheList(Constants.BLOG_SEARCH_BY_TAG_ID + tagId);
        Integer total = redisCache.getCacheObject(Constants.BLOG_SEARCH_BY_TAG_TOTAL);
        if (records.size()==0||total==null) {
            QueryWrapper<Blog> wrapper = new QueryWrapper<>();
            wrapper.eq("tag_id", tagId);
             records = blogService.list(wrapper);
            Assert.notNull(records,ResultCode.BLOG_EXCEPTION.getMsg(),ResultCode.BLOG_EXCEPTION.getCode());

            List<Integer> collect2 = records.stream().map(record -> record.getTagId()).collect(Collectors.toList());

            List<Tag> otag = tagMapper.selectBatchIds(collect2);
            for (Blog record : records) {
                List<Tag> categories3 = otag.stream().filter(c -> record.getTagId().equals(c.getId())).collect(Collectors.toList());
                for (Tag tag : categories3) {
                    record.setTagName(tag.getName());
                }
            }
            Page<Blog> blogPage = blogService.page(new Page<>());
             total = (int) blogPage.getTotal();
            redisCache.setCacheList(Constants.BLOG_SEARCH_BY_TAG_ID + tagId, records);
            redisCache.setCacheObject(Constants.BLOG_SEARCH_BY_TAG_TOTAL, total);
            redisCache.expire(Constants.BLOG_SEARCH_BY_TAG_TOTAL, 1, TimeUnit.HOURS);
            redisCache.expire(Constants.BLOG_SEARCH_BY_TAG_ID + tagId, 1, TimeUnit.HOURS);
        }
        if (records.size()!=0&&total!=null){
            redisCache.getCacheList(Constants.BLOG_SEARCH_BY_TAG_ID + tagId);
            return Result.ok().data("records", records).data("total", total);
        }
        return Result.ok().data("records", records).data("total", total);
    }

}

