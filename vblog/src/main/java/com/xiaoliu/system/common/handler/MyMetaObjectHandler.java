package com.xiaoliu.system.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.xiaoliu.system.common.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: 61分
 * @date: 2022/8/1 22:38
 * @description:
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill");
        this.strictInsertFill(metaObject, "createdTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "modifiedTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "status", Integer.class, Constants.STATUS_NORMAL);
        this.strictInsertFill(metaObject, "role", String.class, Constants.USER);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill");
        this.strictInsertFill(metaObject, "modifiedTime", Date.class, new Date());
    }
}