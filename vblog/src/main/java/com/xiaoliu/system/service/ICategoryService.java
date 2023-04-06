package com.xiaoliu.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaoliu.system.VO.CategoryVO;
import com.xiaoliu.system.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaoliu
 * @since 2022-08-15
 */
public interface ICategoryService extends IService<Category> {

    List<CategoryVO> getList();


    List<Category> searchCount();
}
