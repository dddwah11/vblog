package com.xiaoliu.system.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: 61分
 * @date: 2022/8/1 22:54
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyUserDTO implements Serializable {
    private static final long serialVersionUID= -1L;
    private String username;
    private String password;
}
