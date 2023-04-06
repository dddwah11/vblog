package com.xiaoliu.system.service;

import com.xiaoliu.system.entity.Type;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaoliu
 * @since 2022-11-05
 */
public interface ITypeService extends IService<Type> {

    List<Type> listAll();
}
