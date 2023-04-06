package com.xiaoliu.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaoliu.system.entity.Blog;
import com.xiaoliu.system.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xiaoliu
 * @since 2022-08-24
 */
public interface TagMapper extends BaseMapper<Tag> {

    @Select("SELECT t.id, t.name,count(u.id) as count from sys_tag t LEFT JOIN t_blog u on u.tag_id = t.id GROUP BY t.id;")
    List<Tag> findTag();
}
