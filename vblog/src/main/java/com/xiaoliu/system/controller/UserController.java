package com.xiaoliu.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaoliu.system.common.config.RedisCache;
import com.xiaoliu.system.common.constants.Constants;
import com.xiaoliu.system.common.handler.BusinessException;
import com.xiaoliu.system.common.response.Result;
import com.xiaoliu.system.common.response.ResultCode;
import com.xiaoliu.system.common.utils.JwtUtils;
import com.xiaoliu.system.controller.DTO.LoginDTO;
import com.xiaoliu.system.VO.UserVO;
import com.xiaoliu.system.entity.User;
import com.xiaoliu.system.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xiaoliu
 * @since 2022-07-31
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {


    @Resource
    private IUserService userService;

    /**
     * 登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO user){
        UserVO vo = userService.login(user);
        String jwtToken = JwtUtils.getJwtToken(vo.getId().toString(), vo.getUsername());
        vo.setToken(jwtToken);
        return Result.ok().data("user",vo).data("menus",vo.getMenu());
    }

    /**
     * 登出
     */
    @PostMapping("/logout")
    public Result logout(){
        return Result.ok();
    }


    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping("/reg")
    public Result reg(@RequestBody User user){
        userService.reg(user);
        return Result.ok();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @PostMapping("/del/{id}")
    public Result del(@PathVariable Integer id){
        userService.del(id);
        return Result.ok();
    }

    /**
     * 条件分页查询
     * @param current
     * @param size
     * @param user
     * @return
     */

    @PostMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer current,
                       @RequestParam(defaultValue = "7") Integer size,
                       @RequestBody User user){
        IPage<User> page = userService.Search(current, size, user);
        long total = page.getTotal();
        List<User> records = page.getRecords();
        return Result.ok().data("records",records).data("total",total);
    }

    /**
     * 更新
     * @param user
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody User user){
        userService.updateById(user);
        return Result.ok();
    }


}

