package com.zz.framework.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * JdbcTemplate工具类
 * @author yanjunhao
 * @date 2017.10.17
 */
public class JdbcTemplateUtil {
    private static Log logger = LogFactory.getLog(JdbcTemplateUtil.class);
    /**
     * 获取基于默认数据源的jdbcTemplate
     */
    public static JdbcTemplate getInstance(){
        DataSource dataSource = (DataSource) ApplicationContextUtil.getBean("defaultDatasource");
        return new JdbcTemplate(dataSource);
    }

}
