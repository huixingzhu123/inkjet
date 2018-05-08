package com.zz.inkjet.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zz.framework.domain.AuditEntity;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * demo
 * @author yanjunhao
 * @date 2017年10月30日
 */
@Entity
@Table(name = "B_CONTACT_MSG")
public class B_Contact_Msg extends AuditEntity {

    @NotEmpty(message = "name不能为空")
    @Column(name = "NAME")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;

    @Column(name = "EMAIL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    @NotNull(message = "message不能为空")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "ISREAD")
    private String isRead;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
