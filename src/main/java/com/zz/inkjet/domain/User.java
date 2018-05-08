/**
 * Copyright(C),2017-2018,广州华资软件技术有限公司
 * FileName: User
 * Author: zengweiqiang
 * Date: 2018/4/25  17:48
 * Description:
 * History:
 * <author>     <time>      <version>       <desc>
 * 作者姓名     修改时间        版本号         描述
 */
package com.zz.inkjet.domain;

import com.zz.framework.domain.AuditEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by zengweiqiang on 2018/4/25.
 */
@Entity
@Table(name = "USER")
public class User extends AuditEntity {
    private  String username;
    private String password;
    private String dept;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
