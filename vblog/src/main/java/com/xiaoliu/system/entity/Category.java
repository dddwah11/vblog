package com.xiaoliu.system.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiaoliu
 * @since 2022-08-15
 */
@TableName("sys_category")
//@ApiModel(value = "Category对象", description = "")
@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;


//    @ApiModelProperty("状态")
    @TableField(fill = FieldFill.INSERT)
    private Integer status;

    @TableField(exist = false)
    private Integer count;


}
