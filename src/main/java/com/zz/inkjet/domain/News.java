/**
 * Copyright(C),2017-2018,广州华资软件技术有限公司
 * FileName: News
 * Author: zengweiqiang
 * Date: 2018/5/1  0:57
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
 * Created by zengweiqiang on 2018/5/1.
 */
@Entity
@Table(name = "NEWS")
public class News  extends AuditEntity {
    private String title;
    private String content;
    private String author;
    private String summary;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
