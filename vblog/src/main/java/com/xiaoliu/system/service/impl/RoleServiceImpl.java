package com.xiaoliu.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoliu.system.common.response.ResultCode;
import com.xiaoliu.system.common.handler.BusinessException;
import com.xiaoliu.system.entity.Menu;
import com.xiaoliu.system.entity.Role;
import com.xiaoliu.system.entity.RoleMenu;
import com.xiaoliu.system.mapper.RoleMapper;
import com.xiaoliu.system.mapper.RoleMenuMapper;
import com.xiaoliu.system.service.IMenuService;
import com.xiaoliu.system.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaoliu
 * @since 2022-08-02
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private IMenuService menuService;

    @Override
    public boolean deleteById(Integer id) {
        int i = this.baseMapper.deleteById(id);
        if (i!=1){
            throw new BusinessException(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg());
        }
        return true;
    }

    //    保证事务一致
    @Transactional
    @Override
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
        roleMenuMapper.deleteByRoleId(roleId);
        for (Integer menuId : menuIds) {
            Menu menu = menuService.getById(menuId);
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
        }
    }

    @Override
    public List<Integer> getRoleMenu(Integer roleId) {
        return roleMenuMapper.selectByRoleId(roleId);
    }

    @Override
    public Page<Role> roleSearch(Integer current, Integer size) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        Page<Role> page = new Page<>(current, size);
        Page<Role> page1 = roleMapper.selectPage(page, wrapper);
        return page1;
    }

    @Override
    public boolean reg(Role role) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("name", role.getName());
        List<Role> list = list(wrapper);
        if (list.size()!=0) {
            throw new BusinessException(ResultCode.USER_EXISTED.getCode(),ResultCode.USER_EXISTED.getMsg());
        }
        int insert = this.baseMapper.insert(role);
        if (insert!=1){
            throw new BusinessException(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg());
        }
        return true;
    }


}
