package com.xiaoliu.system.job;

import com.xiaoliu.system.common.config.RedisCache;
import com.xiaoliu.system.common.constants.Constants;
import com.xiaoliu.system.entity.Blog;
import com.xiaoliu.system.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: 61分
 * @date: 2022/9/1 19:04
 * @description:
 */
@Component
public class UpdateViewCountJob {
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private IBlogService blogService;

    /**
     * 定时10分钟更新浏览量
     * 下面的注解表示每年每月每日每分的一分开始，每10分0秒更新一次
     * 效果：
     * 2022-11-24 06:21:00
     * 定时10分钟更新文章浏览量
     * 2022-11-24 06:31:00
     */
    @Scheduled(cron = "0 1/10 * * * ?")
    public void updateViewCount(){
//        获取redis中的浏览量
        Map<String, Integer> cacheMap = redisCache.getCacheMap(Constants.BLOGVIECOUT);

        List<Blog> list = cacheMap.entrySet()
                .stream().map(entry -> new Blog(Integer.valueOf(entry.getKey()), entry.getValue()))
                .collect(Collectors.toList());
//        存入到数据库中
        blogService.updateBatchById(list);
        redisCache.delCacheMap(Constants.BLOG_ALL_PAGE);
        System.out.println("定时10分钟更新文章浏览量");
    }


}
