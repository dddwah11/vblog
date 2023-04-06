package com.xiaoliu.system.service.impl;

import com.xiaoliu.system.entity.Chat;
import com.xiaoliu.system.mapper.ChatMapper;
import com.xiaoliu.system.service.IChatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaoliu
 * @since 2022-08-25
 */
@Service
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat> implements IChatService {

}
