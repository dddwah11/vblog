package com.xiaoliu.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
 * @since 2022-08-02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_menu")
//@ApiModel(value = "Menu对象", description = "")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

//    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

//    @ApiModelProperty("名称")
    private String name;

//    @ApiModelProperty("路径")
    private String path;

//    @ApiModelProperty("图标")
    private String icon;

//    @ApiModelProperty("描述")
    private String description;

//    @ApiModelProperty("父级id")
    private Integer pid;

//    @ApiModelProperty("页面路径")
    private String pagePath;

//    @ApiModelProperty("顺序")
    private Integer sortNum;

    @TableField(exist = false)
    private List<Menu> children;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    @Override
    public String toString() {
        return "Menu{" +
        "id=" + id +
        ", name=" + name +
        ", path=" + path +
        ", icon=" + icon +
        ", description=" + description +
        ", pid=" + pid +
        ", pagePath=" + pagePath +
        ", sortNum=" + sortNum +
        "}";
    }
}
