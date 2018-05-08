package com.zz.framework.support.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.ThreadFactory;

/**
 * 日志处理线程构造工厂
 *
 * @author yanjunhao
 * @date 2017年11月1日
 */
public class LoggingThreadFactory implements ThreadFactory {
    private Log logger = LogFactory.getLog(LoggingThreadFactory.class);

    @Override
    public Thread newThread(Runnable r) {
        String name = "log-" + System.currentTimeMillis();
        logger.debug("创建新的日志处理[" + name + "]线程");
        return new Thread(r, name);
    }
}
