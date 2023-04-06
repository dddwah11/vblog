package com.xiaoliu.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
 * @since 2022-08-26
 */
@Getter
@Setter
@TableName("t_link")
//@ApiModel(value = "Link对象", description = "")
public class Link implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

//    @ApiModelProperty("博客名称")
    private String title;

//    @ApiModelProperty("博客图片")
    private String photoUrl;

//    @ApiModelProperty("地址")
    private String site;


}
