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
 * @since 2022-08-29
 */
@Getter
@Setter
@TableName("t_photo")
//@ApiModel(value = "Photo对象", description = "")
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

//    @ApiModelProperty("照片名")
    private String name;

//    @ApiModelProperty("地点")
    private String region;

//    @ApiModelProperty("描述")
    private String description;

//    @ApiModelProperty("图片url")
    private String url;

//    @ApiModelProperty("文件名")
    private String fileName;



}
