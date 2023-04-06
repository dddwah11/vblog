package com.xiaoliu.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoliu.system.common.config.RedisCache;
import com.xiaoliu.system.common.constants.Constants;
import com.xiaoliu.system.common.handler.BusinessException;
import com.xiaoliu.system.common.response.Result;
import com.xiaoliu.system.common.response.ResultCode;
import com.xiaoliu.system.entity.Dict;
import com.xiaoliu.system.entity.Menu;
import com.xiaoliu.system.mapper.DictMapper;
import com.xiaoliu.system.service.IMenuService;
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

@RestController
@RequestMapping("/menu")
@CrossOrigin
public class MenuController {
    @Resource
    private IMenuService menuService;

    @Resource
    private DictMapper dictMapper;

    /**
     * 新增
     * @param menu
     * @return
     */
    @PostMapping("/reg")
    public Result reg(@RequestBody Menu menu){
        if (!menuService.reg(menu)) {
            return Result.error();
        }
        return Result.ok();
    }

    /**
     * 根据id修改
     * @param menu
     * @return
     */
    @PostMapping("/update")
    public Result updateById(@RequestBody Menu menu){
        if (!menuService.updateById(menu)){
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
    public Result deleteById(@PathVariable Integer id){
        if (!menuService.deleteById(id)) {
            return Result.error();
        }
        return Result.ok();
    }



    @GetMapping("/findAll")
    public Result findAll(){
        List<Menu> parentNodes = menuService.findMenus();
        return Result.ok().data("parentNode",parentNodes);
    }

    @GetMapping("/icons")
    public Result getIcons(){
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("type", "icon");
        List<Dict> icons = dictMapper.selectList(wrapper);
        return Result.ok().data("icons",icons);
    }
}

