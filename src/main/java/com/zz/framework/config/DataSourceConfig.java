package com.zz.framework.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 数据源配置
 * 包含一个默认的数据源和一个动态数据源
 *
 * @author yanjunhao
 * @date 2017.09.25
 */
@Configuration
public class DataSourceConfig {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);


    @Bean(name = "defaultDatasource")
    @Qualifier("defaultDatasource")
    @ConfigurationProperties(prefix = "spring.datasource.default")
    public DataSource defaultDatasource() {
        DataSource defaultDatasource = DruidDataSourceBuilder.create().build();
        logger.info("[bean]defaultDatasource实例化。");
        if(logger.isDebugEnabled()){
            logger.debug("DataSource start.............");
        }
        return defaultDatasource;
    }
}
