package com.xiaoliu.system.VO;

import lombok.Data;

/**
 * @author: 61分
 * @date: 2022/8/15 21:33
 * @description:
 */

public class CategoryVO {

    private Integer id;
    private String name;

    public CategoryVO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryVO() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
