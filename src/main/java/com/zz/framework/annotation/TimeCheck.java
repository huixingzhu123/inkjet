package com.zz.framework.annotation;

import com.zz.framework.validation.TimeCheckValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 扩展校验-用于比较传入时间跟当前时间
 *
 * @author yanjunhao
 * @date 2017年10月19日
 */
@Documented
@Constraint(
        validatedBy = {TimeCheckValidation.class}//通过该类进行扩展
)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeCheck {
    /**
     * 比较类型
     */
    TimeCheckValidation.Type type() default TimeCheckValidation.Type.LT;

    /**
     * 比较的时间来源
     */
    TimeCheckValidation.TimeSource timeSource() default TimeCheckValidation.TimeSource.DATABASE;

    String message() default "cn.sinobest.jzpt.framework.annotation.TimeCheck";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
