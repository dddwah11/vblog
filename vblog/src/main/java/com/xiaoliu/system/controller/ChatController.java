package com.xiaoliu.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoliu.system.common.config.RedisCache;
import com.xiaoliu.system.common.constants.Constants;
import com.xiaoliu.system.common.handler.BusinessException;
import com.xiaoliu.system.common.response.Result;
import com.xiaoliu.system.common.response.ResultCode;
import com.xiaoliu.system.entity.Chat;
import com.xiaoliu.system.service.IChatService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xiaoliu
 * @since 2022-08-25
 */
@CrossOrigin
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Resource
    private RedisCache redisCache;
    @Resource
    private IChatService chatService;

    /**
     * 查询全部list
     * @return
     */
    @PostMapping("/findAll")
    public Result searchChatAll(){
        List<Chat> list = redisCache.getCacheList(Constants.CHAT);
        if (list.size()==0) {
             list = chatService.list();
            redisCache.setCacheList(Constants.CHAT, list);
            redisCache.expire(Constants.CHAT, 1, TimeUnit.HOURS);
        }
        return Result.ok().data("records",list);

    }

    /**
     * 分页
     * @param current
     * @param size
     * @return
     */
    @PostMapping("/searchChat")
    public Result searchChat(@RequestParam(defaultValue = "1") Integer current,
                             @RequestParam(defaultValue = "7") Integer size){
        Page<Chat> page = new Page<>(current, size);
        Page<Chat> page1 = chatService.page(page);
        List<Chat> records = page1.getRecords();
        long total = page1.getTotal();
        return Result.ok().data("records", records).data("total",total);

    }

    /**
     * 删除
     * @param id
     * @return
     */
    @PostMapping("/del/{id}")
    public Result del(@PathVariable Integer id){
        chatService.removeById(id);
        redisCache.delCacheMap(Constants.CHAT);
        return Result.ok();
    }

    /**
     * 新增
     * @param
     * @return
     */
    @PostMapping("/reg")
    public Result reg(@RequestBody Chat chat){
        chatService.saveOrUpdate(chat);
        redisCache.delCacheMap(Constants.CHAT);
        return Result.ok();
    }

}

