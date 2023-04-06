package com.xiaoliu.system.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiaoliu
 * @since 2022-08-20
 */
@Getter
@Setter
@TableName("t_comment")
//@ApiModel(value = "Comment对象", description = "")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer pid;

//    @ApiModelProperty("关联文章id")
    private Integer blogId;

//    @ApiModelProperty("内容")
    private String content;

    @TableField(fill = FieldFill.INSERT)
//    @ApiModelProperty("创建时间")
    private Date createdTime;

//    @ApiModelProperty("创建者")
    private String createBy;

//    @ApiModelProperty("最上级id")
    private Integer originId;

    private String email;

    @TableField(exist = false)
    private List<Comment> children;
    
    @TableField(exist = false)
    private String PUser;


}
