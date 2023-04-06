package com.xiaoliu.system.service.impl;

import com.xiaoliu.system.entity.Files;
import com.xiaoliu.system.mapper.FileMapper;
import com.xiaoliu.system.service.IFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaoliu
 * @since 2022-08-18
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, Files> implements IFileService {

}
