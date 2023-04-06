package com.xiaoliu.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoliu.system.common.config.RedisCache;
import com.xiaoliu.system.common.constants.Constants;
import com.xiaoliu.system.common.handler.BusinessException;
import com.xiaoliu.system.common.response.Result;
import com.xiaoliu.system.common.response.ResultCode;
import com.xiaoliu.system.entity.Role;
import com.xiaoliu.system.service.IRoleService;
//import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xiaoliu
 * @since 2022-08-02
 */
@CrossOrigin
@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private IRoleService roleService;


    /**
     * 新增
     * @param role
     * @return
     */
    @PostMapping("/reg")
//    @ApiOperation(value = "用户注册")
    public Result reg(@RequestBody Role role){
        if (!roleService.reg(role)) {
            return Result.error();
        }
        return Result.ok();
    }

    /**
     * 分页查询
     * @param current
     * @param size
     * @return
     */
    @PostMapping("/roleSearch")
    public Result listRole(@RequestParam(defaultValue = "1") Integer current,
                           @RequestParam(defaultValue = "7") Integer size){
        Page<Role> records = roleService.roleSearch(current, size);
        long total = records.getTotal();
        return Result.ok().data("records", records).data("total",total);
    }

    /**
     * 根据id修改
     * @param role
     * @return
     */
    @PostMapping("/update")
    public Result updateById(@RequestBody Role role){
        if (!roleService.updateById(role)){
            return Result.error();
        }
        return Result.ok();
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @PostMapping("/{id}")
//    @ApiOperation(value = "根据id删除")
    public Result deleteById(@PathVariable Integer id){
        if (!roleService.deleteById(id)) {
            return Result.error();
        }
        return Result.ok();
    }

    /**
     * 绑定rolemenu之间的关系（前端的分配菜单之后重新登录获取菜单）
     * @param roleId
     * @param menuIds
     * @return
     */
    @PostMapping("/roleMenu/{roleId}")
//    @ApiOperation(value = "角色菜单关系")
    public Result deleteById(@PathVariable Integer roleId, @RequestBody List<Integer> menuIds){
        roleService.setRoleMenu(roleId,menuIds);
        return Result.ok();
    }

    /**
     *根据角色id返回所能访问的菜单id，即回显到分配菜单的el-tree上
     * @param roleId
     * @return
     */
    @GetMapping("/roleMenu/{roleId}")
//    @ApiOperation(value = "返回关系菜单")
    public Result getDeleteById(@PathVariable Integer roleId){
        List<Integer> roleId1 = roleService.getRoleMenu(roleId);
        return Result.ok().data("checks",roleId1);
    }

}

