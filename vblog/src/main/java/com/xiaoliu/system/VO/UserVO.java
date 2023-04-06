package com.xiaoliu.system.VO;

import com.xiaoliu.system.entity.Menu;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 61分
 * @date: 2022/7/30 21:40
 * @description:
 */
@Data
public class UserVO implements Serializable {

    private static final long serialVersionUID = -1L;

    private String username;

    private Integer id;

    private List<Menu> menu;

    private String role;

    private String token;

}
