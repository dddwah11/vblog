package com.xiaoliu.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiaoliu
 * @since 2022-11-05
 */
@Getter
@Setter
@TableName("sys_source")
public class Source implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String descrption;

    private String url;

    private Integer typeId;

    private String pic;

    @TableField(exist = false)
    private String type;


}
