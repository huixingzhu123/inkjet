package com.zz.framework.support.log.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 日志表
 *
 * @author yanjunhao
 * @date 2017年10月31日
 */
@Entity
@Table(name = "S_LOGGING")
public class LoggingEntity implements Serializable {
    @Id
    @GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
    @GeneratedValue(generator = "hibernate-uuid")
    @Column(name = "systemid", nullable = false)
    private String systemid;
    @Column(name = "authuser")
    private String authuser;
    @Column(name = "datarecordlevel")
    private String datarecordlevel;
    @Column(name = "department")
    private String department;
    @Column(name = "errmsg")
    private String errmsg;
    @Column(name = "info")
    private String info;
    @Column(name = "lasttime")
    private long lasttime;
    @Column(name = "recordtime")
    private Date recordtime;
    @Column(name = "requestip")
    private String requestip;
    @Column(name = "title")
    private String title;
    @Column(name = "type")
    private String type;
    @Column(name = "functionname")
    private String functionname;
    @Column(name = "params")
    private String params;

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

    public String getDatarecordlevel() {
        return datarecordlevel;
    }

    public void setDatarecordlevel(String datarecordlevel) {
        this.datarecordlevel = datarecordlevel;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public long getLasttime() {
        return lasttime;
    }

    public void setLasttime(long lasttime) {
        this.lasttime = lasttime;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFunctionname() {
        return functionname;
    }

    public void setFunctionname(String functionname) {
        this.functionname = functionname;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }


}
