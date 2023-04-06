package com.xiaoliu.system.common.handler;

import lombok.Getter;

/**
 * @author: 61分
 * @date: 2022/8/1 20:47
 * @description:
 */
@Getter
public class BusinessException extends RuntimeException{
    private Integer code;
    private String msg;

    public BusinessException(Integer code, String msg) {
        this.msg = msg;
        this.code = code;
    }
}
