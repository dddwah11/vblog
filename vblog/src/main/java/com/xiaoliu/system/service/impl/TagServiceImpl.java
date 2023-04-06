package com.xiaoliu.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaoliu.system.entity.Tag;
import com.xiaoliu.system.mapper.TagMapper;
import com.xiaoliu.system.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaoliu
 * @since 2022-08-24
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    @Resource
    private TagMapper tagMapper;

    @Override
    public List<Tag> findTag() {
        List<Tag> tag = tagMapper.findTag();
        return tag;
    }
}
