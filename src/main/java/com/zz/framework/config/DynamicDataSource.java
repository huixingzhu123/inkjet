package com.zz.framework.config;

import com.zz.framework.util.DynamicDataSourceContextUtil;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 * @author yanjunhao
 * @date 2017年10月12日
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextUtil.getDataSourceName();
    }
}
