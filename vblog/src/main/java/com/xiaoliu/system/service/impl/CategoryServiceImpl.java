package com.xiaoliu.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoliu.system.common.constants.Constants;
import com.xiaoliu.system.VO.CategoryVO;
import com.xiaoliu.system.entity.Blog;
import com.xiaoliu.system.entity.Category;
import com.xiaoliu.system.mapper.CategoryMapper;
import com.xiaoliu.system.service.IBlogService;
import com.xiaoliu.system.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xiaoliu
 * @since 2022-08-15
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {


    @Resource
    private IBlogService blogService;

    @Resource
    private CategoryMapper categoryMapper;

    //查找blog中有什么分类
    @Override
    public List<CategoryVO> getList() {
        LambdaQueryWrapper<Blog> blogLambdaQueryWrapper = new LambdaQueryWrapper<>();
        blogLambdaQueryWrapper.eq(Blog::getStatus, Constants.STATUS_NORMAL);
        List<Blog> blogs = blogService.list(blogLambdaQueryWrapper);
        //获取文章id并去重
        List<Integer> categoryId = blogs.stream().map(Blog::getCategoryId)
                .collect(Collectors.toList());
        //查询分类表
        List<Category> list = listByIds(categoryId);

        list.stream().filter(category -> Constants.STATUS_NORMAL.equals(category.getStatus()));
        //封装vo
        List<CategoryVO> voList = new ArrayList<>();
        for (Category category : list) {
            CategoryVO categoryVO = new CategoryVO();
            BeanUtil.copyProperties(category,categoryVO);
            voList.add(categoryVO);
        }
        return voList;

    }


    @Override
    public List<Category> searchCount() {
        List<Category> count = categoryMapper.searchCount();
        return count;
    }
}
