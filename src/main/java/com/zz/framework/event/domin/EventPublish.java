package com.zz.framework.event.domin;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "event_publish")
public class EventPublish implements  Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
    @GeneratedValue(generator = "hibernate-uuid")
    @Column(name = "systemid", nullable = false)
    private String systemid;

    @Column
    private String payload;

    @Column
    private String status;

    @Column(unique = true)
    private String eventId;

    @Column
    private String eventType;

    /**
     * 创建时间，不能更新
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "CREATEDTIME",updatable = false)
    private Date createdTime;

    public void setSystemid(String systemid) {
        this.systemid = systemid;
    }

    public String getSystemid() {
        return this.systemid;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
