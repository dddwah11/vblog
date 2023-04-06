package com.xiaoliu.system.service.impl;

import com.xiaoliu.system.entity.Photo;
import com.xiaoliu.system.mapper.PhotoMapper;
import com.xiaoliu.system.service.IPhotoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaoliu
 * @since 2022-08-29
 */
@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, Photo> implements IPhotoService {

}
