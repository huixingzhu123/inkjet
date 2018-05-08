package com.zz.framework.validation;

import com.zz.framework.annotation.TimeCheck;
import com.zz.framework.util.DateUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

/**
 * 时间校验器
 *
 * @author yanjunhao
 * @date 2017年10月19日
 */
public class TimeCheckValidation implements ConstraintValidator<TimeCheck, Date> {
    /**
     * 比较类型
     */
    public enum Type {
        /**
         * LT 小于
         */
        LT,
        /**
         * LTE 小于等于
         */
        LTE,
        /**
         * GT 大于
         */
        GT,
        /**
         * GTE 大于等于
         */
        GTE,
        /**
         * EQ 等于
         */
        EQ,
        /**
         * NE 不等于
         */
        NE
    }

    /**
     * 比较的时间来源
     */
    public enum TimeSource {
        /**
         * database 数据库时间
         */
        DATABASE,
        /**
         * local 应用时间
         */
        LOCAL
    }

    private Type type;
    private TimeSource timeSource;

    @Override
    public void initialize(TimeCheck timeCheck) {
        this.timeSource = timeCheck.timeSource();
        this.type = timeCheck.type();
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        boolean flag;
        Date compareDate = null;
        //如果传入时间为空，不判断，返回true
        if (null == date) {
            flag = true;
        } else {
            switch (this.timeSource) {
                case DATABASE:
                    compareDate = DateUtil.getDatabaseLocalTime();
                    break;
                case LOCAL:
                    compareDate = DateUtil.getSystemLocalTime();
                    break;
                default:
                    break;
            }
            flag = compare(date, compareDate, this.type);

        }
        return flag;
    }

    private boolean compare(Date date, Date compareDate, Type type) {
        long dateLong = date.getTime();
        long compareDateLong = compareDate.getTime();
        switch (this.type) {
            case LT:
                //小于
                return dateLong < compareDateLong;
            case LTE:
                //小于等于
                return dateLong <= compareDateLong;
            case GT:
                //大于
                return dateLong > compareDateLong;
            case GTE:
                //大于等于
                return dateLong >= compareDateLong;
            case EQ:
                //等于
                return dateLong == compareDateLong;
            case NE:
                //不等于
                return dateLong != compareDateLong;
            default:
                break;
        }
        return false;
    }
}
