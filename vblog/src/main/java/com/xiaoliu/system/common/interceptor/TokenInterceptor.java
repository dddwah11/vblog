package com.xiaoliu.system.common.interceptor;

import com.xiaoliu.system.common.handler.BusinessException;
import com.xiaoliu.system.common.response.Result;
import com.xiaoliu.system.common.response.ResultCode;
import com.xiaoliu.system.common.utils.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: 61分
 * @Date: 2022-11-02 10:58
 * @Description:
 */
@Component
@CrossOrigin
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        跨域预检放行一个options请求
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }


//        获取请求头中的token进行验证
        response.setCharacterEncoding("UTF-8");
        String token = request.getHeader("Authorization");
        if (token != null) {
            boolean b = JwtUtils.checkToken(token);
            if (b) {
                System.out.println("验证成功");
                return true;
            }
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        return false;
    }
}
