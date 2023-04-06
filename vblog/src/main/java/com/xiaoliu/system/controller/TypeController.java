package com.xiaoliu.system.controller;


import com.xiaoliu.system.common.handler.BusinessException;
import com.xiaoliu.system.common.response.Result;
import com.xiaoliu.system.common.response.ResultCode;
import com.xiaoliu.system.entity.Source;
import com.xiaoliu.system.entity.Type;
import com.xiaoliu.system.service.ITypeService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.xiaoliu.system.common.constants.Constants.SOURCE_LIST;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xiaoliu
 * @since 2022-11-05
 */
@RestController
@RequestMapping("/type")
public class TypeController {
    @Resource
    private ITypeService typeService;
    @PostMapping("/reg")
    public Result reg(@RequestBody Type type) {
        try {
            typeService.save(type);
            return Result.ok();
        } catch (Exception e) {
            throw new BusinessException(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg());
        }
    }


    @PostMapping("/del/{id}")
    public Result reg(@PathVariable("id") Integer id) {
        try {
            typeService.removeById(id);
            return Result.ok();
        } catch (Exception e) {
            throw new BusinessException(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg());
        }
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody Type type) {
        try {
            typeService.updateById(type);
            return Result.ok();
        } catch (Exception e) {
            throw new BusinessException(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg());
        }
    }

    @GetMapping("/listAll")
    public Result listAll() {
            try {
               List<Type> list = typeService.listAll();
                return Result.ok().data("records", list);
            } catch (Exception e) {
                throw new BusinessException(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg());
            }
    }
}

