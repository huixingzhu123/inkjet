package com.zz.framework.annotation;

import java.lang.annotation.*;

/**
 * 服务层的方法上使用，用于指定使用哪个数据源
 * 只对jdbcTemple有效，spring data jpa还是使用默认数据源
 *
 * @author yanjunhao
 * @date 2017年10月12日
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    //不进行指定的话为默认数据源
    String value() default "defaultDatasource";
}
