package com.zz.framework.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * 包含审计功能的实体类，此处定义的字段内容由系统自动生成，不用另外处理
 *
 * @author yanjunhao
 * @date 2017.10.09
 */
@MappedSuperclass
public class AuditEntity extends com.zz.framework.domain.BaseEntity {



    /**
     * 创建时间，不能更新
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    @Column(name = "CREATEDTIME",updatable = false)
    private Date createdTime;

    /**
     * 最后更新时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
    @Column(name = "LASTUPDATEDTIME")
    private Date lastUpdatedTime;

    /**
     * 创建人，不能更新
     */
    @CreatedBy
    @Column(name = "CREATOR",updatable = false)
    private String creator;

    /**
     * 最后更新人
     */
    @LastModifiedBy
    @Column(name = "LASTUPDATEDBY")
    private String lastUpdatedBy;

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
