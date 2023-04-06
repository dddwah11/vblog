package com.xiaoliu.system.mapper;

import com.xiaoliu.system.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xiaoliu
 * @since 2022-08-20
 */
public interface CommentMapper extends BaseMapper<Comment> {


    @Select("select  t.* from (SELECT * from t_comment where t_comment.blog_id = #{blogId}) t order by t.id desc;")
    List<Comment> findTreeById(@Param("blogId") Integer blogId);
}
