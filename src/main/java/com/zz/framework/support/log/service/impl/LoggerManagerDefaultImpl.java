package com.zz.framework.support.log.service.impl;

import com.zz.framework.support.log.entity.ChangeLog;
import com.zz.framework.support.log.entity.LoggingEntity;
import com.zz.framework.support.log.repository.ChangeLogRepository;
import com.zz.framework.support.log.repository.LoggingEntityRepository;
import com.zz.framework.support.log.service.LoggingManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 日志管理默认实现类
 *
 * @author yanjunhao
 * @date 2017年11月1日
 */
@Service("loggerManagerDefault")
public class LoggerManagerDefaultImpl implements LoggingManager {
    private static final Logger logger = LoggerFactory.getLogger(LoggerManagerDefaultImpl.class);

    private final LoggingEntityRepository loggingEntityRepository;
    private final ChangeLogRepository changeLogRepository;

    @Autowired
    public LoggerManagerDefaultImpl(LoggingEntityRepository loggingEntityRepository, ChangeLogRepository changeLogRepository) {
        this.loggingEntityRepository = loggingEntityRepository;
        this.changeLogRepository = changeLogRepository;
    }

    @Transactional(
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class
    )
    @Override
    public void saveLoggingWithChangelog(LoggingEntity logging, ChangeLog changeLog) {
        LoggingEntity saveLog = loggingEntityRepository.save(logging);
        if (null != changeLog) {
            changeLog.setLoggingid(saveLog.getSystemid());
            changeLogRepository.save(changeLog);
        }
    }

    @Override
    public void saveLoggingWithoutChangelog(LoggingEntity logging) {
        try {
            loggingEntityRepository.save(logging);
        } catch (Exception e) {
            logger.error("saveLoggingWithoutChangelog is...",e);
        }
    }
}
