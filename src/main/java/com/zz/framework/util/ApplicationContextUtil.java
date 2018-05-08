package com.zz.framework.util;

import org.springframework.context.ApplicationContext;

/**
 * 上下文工具类
 *
 * @author yanjunhao
 * @date 2017.09.29
 */
public class ApplicationContextUtil {
    private static ApplicationContext applicationContext;

    /**
     * 获取上下文对象
     *
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 设置上下文对象
     *
     * @param applicationContext
     */
    public static void setApplicationContext(ApplicationContext applicationContext) {
        ApplicationContextUtil.applicationContext = applicationContext;
    }

    /**
     * 通过名字获取上下文中的bean
     */
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    /**
     * 通过类型获取上下文中的bean
     */
    public static Object getBean(Class<?> requiredType) {
        return applicationContext.getBean(requiredType);
    }
}
