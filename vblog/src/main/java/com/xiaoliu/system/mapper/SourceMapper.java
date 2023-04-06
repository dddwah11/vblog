package com.xiaoliu.system.mapper;

import com.xiaoliu.system.entity.Source;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xiaoliu
 * @since 2022-11-05
 */
public interface SourceMapper extends BaseMapper<Source> {
    List<Source> listAll();
}
