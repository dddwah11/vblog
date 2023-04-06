package com.xiaoliu.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoliu.system.common.constants.Constants;
import com.xiaoliu.system.common.response.ResultCode;
import com.xiaoliu.system.entity.Blog;
import com.xiaoliu.system.common.handler.BusinessException;
import com.xiaoliu.system.mapper.BlogMapper;
import com.xiaoliu.system.service.IBlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaoliu
 * @since 2022-07-31
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService {
    @Resource
    private BlogMapper blogMapper;

    @Override
    public void saveBlog(Blog blog) {
        Assert.notNull(blog,ResultCode.BLOG_EXCEPTION.getMsg(),ResultCode.BLOG_EXCEPTION.getCode());
        blogMapper.insert(blog);
    }

    @Override
    public void editBlog(Blog blog) {

        List<Blog> one = list(new QueryWrapper<Blog>().eq("status", Constants.STATUS_NORMAL));
        if (one.size()==0){
            throw new BusinessException(ResultCode.BLOG_EXCEPTION.getCode(), ResultCode.BLOG_EXCEPTION.getMsg());
        }
        blogMapper.updateById(blog);
    }

    @Override
    public void del(Integer id) {
        Blog one = getOne(new QueryWrapper<Blog>().eq("id", id));
        Assert.notNull(one,ResultCode.BLOG_EXCEPTION.getMsg(),ResultCode.BLOG_EXCEPTION.getCode());
        blogMapper.deleteById(one);
    }

    @Override
    public IPage<Blog> listBlog(Integer current, Integer size,Blog blog) {
        Page<Blog> page = new Page<>(current, size);
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        //        是否置顶 按时间排序,
        wrapper.orderByDesc("weight")
                .orderByDesc("created_time")
                .like("tittle",blog.getTittle());
        IPage<Blog> blogPage = blogMapper.searchPage(page, wrapper);
        return blogPage;
        }




}
