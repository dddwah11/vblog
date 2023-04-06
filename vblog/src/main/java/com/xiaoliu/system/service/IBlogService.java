package com.xiaoliu.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaoliu.system.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaoliu
 * @since 2022-07-31
 */
public interface IBlogService extends IService<Blog> {

    void saveBlog(Blog blog);

    void editBlog(Blog blog);

    void del(Integer id);

    IPage<Blog> listBlog(Integer current, Integer size, Blog blog);


}
