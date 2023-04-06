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
 * @since 2022-08-18
 */
@Getter
@Setter
@TableName("sys_file")
//@ApiModel(value = "File对象", description = "")
public class Files implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

//    @ApiModelProperty("文件名")
    private String name;

//    @ApiModelProperty("文件类型")
    private String type;

//    @ApiModelProperty("文件大小")
    private Long size;

//    @ApiModelProperty("文件地址")
    private String url;

    private String uuid;


//    @ApiModelProperty("是否为图片墙")
    private Integer isPhoto;

}
