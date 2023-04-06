package com.xiaoliu.system.common.config;

import com.xiaoliu.system.common.interceptor.TokenInterceptor;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: 61分
 * @date: 2022/9/10 14:07
 * @description:
 */
@CrossOrigin
@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Resource
    TokenInterceptor tokenInterceptor;

    //最大30线程，外加30队列；【拒绝策略AbortPolicy（默认）： 丢弃任务并抛出异常】
    private static ExecutorService executorService = new ThreadPoolExecutor
            (1, 30, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(3));

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setTaskExecutor(new ConcurrentTaskExecutor(executorService));
        configurer.setDefaultTimeout(30000);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        List<String> excludePath  = new ArrayList<>();
//        excludePath.add("/user/login");
//        excludePath.add("/category/count");
//        excludePath.add("/category/total");
//        excludePath.add("/tag/list");
//        excludePath.add("/tag/all");
//        excludePath.add("/link/list");
//        excludePath.add("/blog/page");
//        excludePath.add("/blog/*");
//        excludePath.add("/blog/list");
//        excludePath.add("/blog/list");
//        excludePath.add("/blog/findAllBlog");
//        excludePath.add("/blog/findByTag/*");
//        excludePath.add("/comment/tree/*");
//        excludePath.add("/comment/reg");
//        excludePath.add("/link/list");
//        excludePath.add("/photo/list");
//        excludePath.add("/chat/findAll");
//        excludePath.add("/chat/reg");
//        excludePath.add("/static/**");
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/user/reg","/user/del/*","/user/page","/user/update",
                        "/tag/del/*","/tag/add",
                        "/role/reg","/role/roleSearch","/role/update","/role/*","/role/roleMenu/*",
                        "/photo/reg","/photo/edit","/photo/del/*",
                        "/menu/reg","/menu/update","/menu/*","/menu/findAll","/menu/icons",
                        "/link/del/*","/link/reg",
                        "/file/upload","/file/uploadPhoto","/file/del/*",
                        "/minio/uploadPhoto","/minio/upload",
                        "/comment/del/*","/chat/searchChat","/chat/del/*",
                        "/blog/save","/blog/edit","/blog/del/*",
                        "/source/reg","source/del/*","/source/edit","/source/list",
                        "/type/reg","/type/del/*","/type/edit");
//                .excludePathPatterns(excludePath);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://www.lhzzz-61.top","lhzzz-61.top","www.lhzzz-61.top","http://114.116.28.238:80",
                        "https://114.116.28.238:80","https://www.lhzzz-61.top","http://114.116.28.238:8081","https://114.116.28.238:8081")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("*")
                .allowedHeaders("*");

    }
}
