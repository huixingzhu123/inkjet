package com.zz.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zz.framework.annotation.TimeCheck;
import com.zz.framework.domain.AuditEntity;
import com.zz.framework.validation.TimeCheckValidation;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * demo
 * @author yanjunhao
 * @date 2017年10月30日
 */
@Entity
@Table(name = "SPRING_DATA_JPA_TEST")
public class Demo extends AuditEntity {

    @NotEmpty(message = "name不能为空")
    @Column(name = "NAME")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;

    @DecimalMin(message = "age不能小于0", value = "0")
    @Column(name = "AGE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int age;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "birthday不能为空")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "BIRTHDAY")
    //时间校验，小于数据库时间
    @TimeCheck(
            type = TimeCheckValidation.Type.LT,
            timeSource = TimeCheckValidation.TimeSource.DATABASE,
            message = "birthday不能大于当前时间。"
    )
    private Date birthday;

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
