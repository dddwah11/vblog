package com.xiaoliu.system.VO;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 61分
 * @date: 2022/8/6 15:46
 * @description:
 */
@Getter
@Setter
public class BlogVO implements Serializable {

    private static final long serialVersionUID = -1L;

    private Integer id;

    private Integer userId;

    private String tittle;

    private String description;

    private String content;

    private Integer status;

    private Integer deleted;

    private Date createdTime;

    private Date modifiedTime;

    private Integer weight;

    private Integer categoryId;

    private Integer viewCounts;

    private Integer bodyId;
    private String thumbnail;

}
