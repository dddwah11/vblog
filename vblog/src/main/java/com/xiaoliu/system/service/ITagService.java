package com.xiaoliu.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaoliu.system.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaoliu
 * @since 2022-08-24
 */
public interface ITagService extends IService<Tag> {

    List<Tag> findTag();
}
