package com.zz.framework.aspect;

import com.zz.framework.annotation.TargetDataSource;
import com.zz.framework.util.DynamicDataSourceContextUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 动态数据源切面
 *
 * @author yanjunhao
 * @date 2017年10月12日
 */
@Aspect
@Order(-10)//保证该AOP在@Transactional之前执行
@Component
public class DynamicDataSourceAspect {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);
    /**
     * 切换数据源
     * 切入点为服务层实现类包含TargetDataSource的方法
     *
     * @param point 切点
     * @param targetDataSource 注解
     */
    @Before(value = "@annotation(targetDataSource) && execution(* cn.sinobest.jzpt.*.service.impl.*.*(..))")
    public void changeDataSource(JoinPoint point, TargetDataSource targetDataSource) {
        String dataSourceName = targetDataSource.value();

        List<String> dataSourceList = DynamicDataSourceContextUtil.getDataSourceNameList();
        if(!dataSourceList.contains(dataSourceName)){
            logger.warn("数据源["+dataSourceName+"]不存在！使用默认数据源[defaultDatasource]--pointCut["+point.getSignature()+"]");
            dataSourceName = "defaultDatasource";
        }else{
            logger.info("切换数据源["+dataSourceName+"]--pointCut["+point.getSignature()+"]");
        }
        //设置到上下文中
        DynamicDataSourceContextUtil.setDataSourceAsLocal(dataSourceName);
    }

    /**
     * 销毁当前数据源信息
     *
     * @param point 切点
     * @param targetDataSource 注解
     */
    @After(value = "@annotation(targetDataSource) && execution(* cn.sinobest.jzpt.*.service.impl.*.*(..))")
    public void restoreDataSource(JoinPoint point, TargetDataSource targetDataSource) {
        logger.info("回收数据源["+targetDataSource.value()+"]--pointCut["+point.getSignature()+"]");
        DynamicDataSourceContextUtil.clear();
    }
}
