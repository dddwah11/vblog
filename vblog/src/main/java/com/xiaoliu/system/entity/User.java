package com.xiaoliu.system.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiaoliu
 * @since 2022-07-31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user")
//@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

//    @ApiModelProperty("用户名")
    private String username;

//    @ApiModelProperty("密码")
    private String password;

    @TableField(fill = FieldFill.INSERT)
//    @ApiModelProperty("状态")
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
//    @ApiModelProperty("创建时间")
    private Date createdTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
//    @ApiModelProperty("修改时间")
    private Date modifiedTime;

    @TableField(fill = FieldFill.INSERT)
//    @ApiModelProperty("角色")
    private String role;

    @TableField(exist = false)
    private List<Menu> menu;
}
