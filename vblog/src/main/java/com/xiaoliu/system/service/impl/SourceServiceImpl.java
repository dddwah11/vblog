package com.xiaoliu.system.service.impl;

import com.xiaoliu.system.entity.Source;
import com.xiaoliu.system.mapper.SourceMapper;
import com.xiaoliu.system.service.ISourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class SourceServiceImpl extends ServiceImpl<SourceMapper, Source> implements ISourceService {

    @Resource
    private SourceMapper sourceMapper;
    @Override
    public List<Source> listAll() {
        return sourceMapper.listAll();
    }
}
