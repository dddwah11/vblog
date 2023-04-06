package com.xiaoliu.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoliu.system.entity.Role;
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
public interface IRoleService extends IService<Role> {

    boolean deleteById(Integer id);

    void setRoleMenu(Integer roleId, List<Integer> menuIds);

    List<Integer> getRoleMenu(Integer roleId);

    Page<Role> roleSearch(Integer current, Integer size);

    boolean reg(Role role);
}
