package com.zz.framework.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * 实体基类，包含所有实体的统一属性及设置
 *
 * @author yanjunhao
 * @date 2017.10.09
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

    /**
     * 主键
     */
    @Id
    @GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
    @GeneratedValue(generator = "hibernate-uuid")
    @Column(name = "systemid", nullable = false)
    private String systemid;



    /**
     * 删除标志，默认为0
     */
    @Column(name = "DELETEFLAG")
    private String deleteflag = "0";



    public void setSystemid(String systemid) {
        this.systemid = systemid;
    }

    public String getSystemid() {
        return this.systemid;
    }


    public String getDeleteflag() {
        return deleteflag;
    }

    public void setDeleteflag(String deleteflag) {
        this.deleteflag = deleteflag;
    }
}
