package com.xiaoliu.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaoliu.system.controller.DTO.LoginDTO;
import com.xiaoliu.system.VO.UserVO;
import com.xiaoliu.system.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaoliu
 * @since 2022-07-31
 */
public interface IUserService extends IService<User> {

    UserVO login(LoginDTO user);

    void reg(User user);

    void del(Integer id);

    IPage<User> Search(Integer current, Integer size, User user);
}
