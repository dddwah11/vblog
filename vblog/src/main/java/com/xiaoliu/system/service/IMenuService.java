package com.xiaoliu.system.service;

import com.xiaoliu.system.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaoliu
 * @since 2022-08-02
 */
public interface IMenuService extends IService<Menu> {

    boolean reg(Menu menu);

    boolean deleteById(Integer id);

    List<Menu> findMenus();
}
