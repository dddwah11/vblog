package com.xiaoliu.system.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;


/**
 * <p>
 * 
 * </p>
 *
 * @author xiaoliu
 * @since 2022-07-31
 */
@TableName("t_blog")
//@ApiModel(value = "Blog对象", description = "")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

//    @ApiModelProperty("标题")
    private String tittle;

//    @ApiModelProperty("描述")
    private String description;

//    @ApiModelProperty("内容")
    private String content;

//    @ApiModelProperty("状态")
    @TableField(fill = FieldFill.INSERT)
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
//    @ApiModelProperty("创建时间")
    private Date createdTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
//    @ApiModelProperty("修改时间")
    private Date modifiedTime;

    private Integer weight;

    private Integer categoryId;

    @TableField(fill = FieldFill.INSERT)
    private Integer viewCounts;

    private String thumbnail;

    private Integer tagId;

    @TableField(exist = false)
    private String categoryName;

    @TableField(exist = false)
    private String tagName;

    public Blog(Integer valueOf, Integer value) {
        this.id =valueOf;
        this.viewCounts = value;
    }
}
