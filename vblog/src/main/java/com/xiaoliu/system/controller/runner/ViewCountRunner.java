package com.xiaoliu.system.controller.runner;

import com.xiaoliu.system.common.config.RedisCache;
import com.xiaoliu.system.common.constants.Constants;
import com.xiaoliu.system.entity.Blog;
import com.xiaoliu.system.mapper.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: 61分
 * @date: 2022/9/1 12:59
 * @description:
 */
@Component
public class ViewCountRunner implements CommandLineRunner {


    @Resource
    private RedisCache redisCache;

    @Resource
    private BlogMapper blogMapper;

    /**项目启动成功就会执行一次
     * 博客的浏览量缓存，永久保存，每10分钟存入一次数据库
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println("进入runner");
        //查询博客信息 id viewcount

        List<Blog> blogs = blogMapper.selectList(null);
        Map<String, Integer> collect = blogs.stream().collect(Collectors.toMap(blog -> blog.getId().toString(), Blog::getViewCounts));
        //存储到redis中
        redisCache.setCacheMap(Constants.BLOGVIECOUT, collect);
        System.out.println("退出runner");
    }
}
