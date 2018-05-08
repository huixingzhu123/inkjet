package com.zz.framework.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态数据源上下文
 * @author yanjunhao
 * @date 2017.10.12
 */
public class DynamicDataSourceContextUtil {
    /**
     * 用于保存所有数据源名称的列表
     */
    private static List<String> dataSourceNameList = new ArrayList<>();
    /**
     * 使用ThreadLocal为每个线程提供独立的变量副本。
     * 此处保存当前线程的数据源的bean name
     */
    private static final ThreadLocal<String> CURRENT_DATA_SOURCE = new ThreadLocal<>();

    /**
     * 设置数据源为当前数据源
     * @param dataSourceName 数据源的bean name
     */
    public static void setDataSourceAsLocal(String dataSourceName){
        CURRENT_DATA_SOURCE.set(dataSourceName);
    }

    /**
     * 返回当前线程数据源 bean name
     * @return String
     */
    public static String getDataSourceName(){
        return CURRENT_DATA_SOURCE.get();
    }

    /**
     * 清空内容
     */
    public static void clear(){
        CURRENT_DATA_SOURCE.remove();
    }

    public static List<String> getDataSourceNameList() {
        return dataSourceNameList;
    }

    public static void setDataSourceNameList(List<String> dataSourceNameList) {
        DynamicDataSourceContextUtil.dataSourceNameList = dataSourceNameList;
    }
}
