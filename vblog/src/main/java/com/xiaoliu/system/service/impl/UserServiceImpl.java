package com.xiaoliu.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoliu.system.common.config.RedisCache;
import com.xiaoliu.system.common.constants.Constants;
import com.xiaoliu.system.common.response.ResultCode;
import com.xiaoliu.system.common.utils.JwtUtils;
import com.xiaoliu.system.controller.DTO.LoginDTO;
import com.xiaoliu.system.VO.UserVO;
import com.xiaoliu.system.entity.Menu;
import com.xiaoliu.system.entity.User;
import com.xiaoliu.system.common.handler.BusinessException;
import com.xiaoliu.system.mapper.RoleMapper;
import com.xiaoliu.system.mapper.RoleMenuMapper;
import com.xiaoliu.system.mapper.UserMapper;
import com.xiaoliu.system.service.IMenuService;
import com.xiaoliu.system.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xiaoliu
 * @since 2022-07-31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private IMenuService menuService;


    @Override
    public UserVO login(LoginDTO user) {
        User one = getOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        Assert.notNull(one, ResultCode.USER_NOT_FOUND.getMsg(), ResultCode.USER_NOT_FOUND.getCode());
        if (!one.getPassword().equals(SecureUtil.md5(user.getPassword()))) {
            throw new BusinessException(ResultCode.PWD_ERROR.getCode(), ResultCode.PWD_ERROR.getMsg());
        }
        String role = one.getRole();
        Integer roleId = roleMapper.selectByUserKey(role);
//            根据roleId查出关系表，根据角色id返回该角色拥有的菜单
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);
//            查出系统全部的菜单
        List<Menu> menus = menuService.findMenus();
//             筛选出当前用户的菜单
//            找出menu与menuIds相同id的菜单 没有就筛选掉，显示有的
//            存放筛选出的菜单
        List<Menu> roleMenus = new ArrayList<>();
//            查出拥有的一级菜单
        // 筛选当前用户角色菜单
        for (Menu menu : menus) {
            if (menuIds.contains(menu.getId())) {
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
//                移除没有的二级菜单
            children.removeIf(child -> ! menuIds.contains(child.getId()));
        }
        one.setMenu(roleMenus);
        UserVO vo = new UserVO();
        BeanUtil.copyProperties(one, vo);
        return vo;
    }

    @Override
    public void reg(User user) {
        User one = getOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        Assert.isNull(one, ResultCode.USER_EXISTED.getMsg(), ResultCode.USER_EXISTED.getCode());
        String md5 = SecureUtil.md5(user.getPassword());
        user.setPassword(md5);
        userMapper.insert(user);
    }

    @Override
    public void del(Integer id) {
        User one = getOne(new QueryWrapper<User>().eq("id", id));
        Assert.notNull(one, ResultCode.USER_NOT_FOUND.getMsg(), ResultCode.USER_NOT_FOUND.getCode());
        userMapper.deleteById(one);
    }

    @Override
    public IPage<User> Search(Integer current, Integer size, User user) {
        Page<User> userPage = new Page<>(current, size);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("username", user.getUsername());
        IPage<User> pageCount = userMapper.Search(userPage,wrapper);
        return pageCount;
    }

}
