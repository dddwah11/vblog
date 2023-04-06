package com.xiaoliu.system.common.handler;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.xiaoliu.system.common.response.Result;
import com.xiaoliu.system.common.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author: 61分
 * @date: 2022/8/1 20:46
 * @description:
 */
@Slf4j
@ControllerAdvice
public class GlobalException{

//    新增一个方法，想要拦截异常类型，注解为目标异常类型
    @ExceptionHandler(MybatisPlusException.class)
    @ResponseBody
    public Result handler(MybatisPlusException e){
        log.error(e.getMessage());
        return Result.error().message(e.getMessage());
    }

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public Result handler(BusinessException e){
        log.error(e.getMessage());
        return Result.error().message(e.getMsg()).code(e.getCode());
    }

    }
