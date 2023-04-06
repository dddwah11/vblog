package com.xiaoliu.system.service;

import com.xiaoliu.system.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaoliu
 * @since 2022-08-20
 */
public interface ICommentService extends IService<Comment> {

    List<Comment> findTreeById(Integer id);
}
