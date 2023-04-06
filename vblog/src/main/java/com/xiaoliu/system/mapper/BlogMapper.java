package com.xiaoliu.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoliu.system.entity.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xiaoliu
 * @since 2022-07-31
 */
public interface BlogMapper extends BaseMapper<Blog> {

    IPage<Blog> searchPage(Page<Blog> page,@Param(Constants.WRAPPER) QueryWrapper<Blog> wrapper);
}
