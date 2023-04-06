package com.xiaoliu.system.service.impl;

import com.xiaoliu.system.entity.Type;
import com.xiaoliu.system.mapper.TypeMapper;
import com.xiaoliu.system.service.ITypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaoliu
 * @since 2022-11-05
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements ITypeService {
    @Resource
    private TypeMapper typeMapper;
    @Override
    public List<Type> listAll() {
        return typeMapper.listAll();
    }
}
