package com.zz.framework.support.log.annotation;


import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Logging {
    String title() default "";

    String info() default "";

    String type() default "0";

    String saveType() default "";

    /**
     * 当需要记录修改日志的时候，修改default为被修改的实体的类
     */
    Class<?> changeObject() default Logging.class;
}
