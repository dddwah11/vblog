package com.xiaoliu.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoliu.system.common.response.ResultCode;
import com.xiaoliu.system.common.handler.BusinessException;
import com.xiaoliu.system.entity.Menu;
import com.xiaoliu.system.mapper.MenuMapper;
import com.xiaoliu.system.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaoliu
 * @since 2022-08-02
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {


    @Override
    public boolean reg(Menu menu) {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.eq("name", menu.getName());
        List<Menu> list = list(wrapper);
        if (list.size()!=0) {
            throw new BusinessException(ResultCode.MENU_EXISTED.getCode(),ResultCode.MENU_EXISTED.getMsg());
        }
        int insert = this.baseMapper.insert(menu);
        if (insert!=1){
            throw new BusinessException(ResultCode.INSERT_EXCEPTION.getCode(), ResultCode.INSERT_EXCEPTION.getMsg());
        }
        return true;
    }

    @Override
    public boolean updateById(Menu menu) {
        int i = this.baseMapper.updateById(menu);
        if (i!=1){
            throw new BusinessException(ResultCode.INSERT_EXCEPTION.getCode(), ResultCode.INSERT_EXCEPTION.getMsg());
        }
        return true;
    }

    @Override
    public boolean deleteById(Integer id) {
        int i = this.baseMapper.deleteById(id);
        if (i!=1){
            throw new BusinessException(ResultCode.INSERT_EXCEPTION.getCode(), ResultCode.INSERT_EXCEPTION.getMsg());
        }
        return true;
    }

    @Override
    public List<Menu> findMenus() {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort_num");
        List<Menu> list = list(wrapper);
//        找出pid为null的一级菜单
        List<Menu> parentNodes = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
//      找出二级菜单
        for (Menu menu : parentNodes) {
            menu.setChildren( list.stream().filter(m -> menu.getId().equals(m.getPid())).collect(Collectors.toList()));
        }
        return parentNodes;
    }
}
