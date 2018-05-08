package com.zz.framework.support.log.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 实体修改日志
 * @author yanjunhao
 * @date 2017年10月31日
 */
@Entity
@Table(name = "S_CHANGELOG")
public class ChangeLog {
    @Id
    @GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
    @GeneratedValue(generator = "hibernate-uuid")
    @Column(name = "systemid", nullable = false)
    private String systemid;

    @Column(name = "authuser")
    private String authuser;

    @Column(name = "department")
    private String department;

    @Column(name = "entityname")
    private String entityname;

    @Column(name = "loggingid")
    private String loggingid;

    @Column(name = "newentity")
    private String newentity;

    @Column(name = "oldentity")
    private String oldentity;

    @Column(name = "recordtime")
    private Date recordtime;

    @Column(name = "requestip")
    private String requestip;

    public String getSystemid() {
        return systemid;
    }

    public void setSystemid(String systemid) {
        this.systemid = systemid;
    }

    public String getAuthuser() {
        return authuser;
    }

    public void setAuthuser(String authuser) {
        this.authuser = authuser;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEntityname() {
        return entityname;
    }

    public void setEntityname(String entityname) {
        this.entityname = entityname;
    }

    public String getLoggingid() {
        return loggingid;
    }

    public void setLoggingid(String loggingid) {
        this.loggingid = loggingid;
    }

    public String getNewentity() {
        return newentity;
    }

    public void setNewentity(String newentity) {
        this.newentity = newentity;
    }

    public String getOldentity() {
        return oldentity;
    }

    public void setOldentity(String oldentity) {
        this.oldentity = oldentity;
    }

    public Date getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(Date recordtime) {
        this.recordtime = recordtime;
    }

    public String getRequestip() {
        return requestip;
    }

    public void setRequestip(String requestip) {
        this.requestip = requestip;
    }
}
