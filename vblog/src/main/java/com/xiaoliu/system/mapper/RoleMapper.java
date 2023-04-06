package com.xiaoliu.system.mapper;

import com.xiaoliu.system.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xiaoliu
 * @since 2022-08-02
 */
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select id from sys_role where user_key = #{role}")
    Integer selectByUserKey(@Param("role") String role);
}
