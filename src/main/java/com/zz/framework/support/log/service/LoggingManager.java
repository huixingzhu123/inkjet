package com.zz.framework.support.log.service;

import com.zz.framework.support.log.entity.ChangeLog;
import com.zz.framework.support.log.entity.LoggingEntity;

/**
 * 日志处理接口
 * @author yanjunhao
 * @date 2017年10月31日
 */
public interface LoggingManager {
    /**
     * 级联保存日志和实体修改日志
     * @param logging 日志
     * @param changeLog 修改日志
     */
    void saveLoggingWithChangelog(LoggingEntity logging, ChangeLog changeLog);

    /**
     * 只保存日志
     * @param logging 日志
     */
    void saveLoggingWithoutChangelog(LoggingEntity logging);
}
