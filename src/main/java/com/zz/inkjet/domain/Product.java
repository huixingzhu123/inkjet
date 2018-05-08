/**
 * Copyright(C),2017-2018,广州华资软件技术有限公司
 * FileName: Product
 * Author: zengweiqiang
 * Date: 2018/5/1  0:52
 * Description:
 * History:
 * <author>     <time>      <version>       <desc>
 * 作者姓名     修改时间        版本号         描述
 */
package com.zz.inkjet.domain;

import com.zz.framework.domain.AuditEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by zengweiqiang on 2018/5/1.
 */
@Entity
@Table(name = "PRODUCTS")
public class Product  extends AuditEntity {
    @Column(name="ITEMID")
    private String itemId;
    @Column(name="cartridgetype")
    private String cartridgeType;
    private String series;
    @Column(name = "oemcode")
    private String oemCode;
    private String color;
    @Column(name="suitablemachine")
    private String suitableMachine;
    @Column(name="pageyield")
    private String pageYield;
    @Column(name="itemtype")
    private String itemType;
    private Integer order;
    private String remarks;
    @Column(name="newarrival")
    private String newArrival;

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getCartridgeType() {
        return cartridgeType;
    }

    public void setCartridgeType(String cartridgeType) {
        this.cartridgeType = cartridgeType;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getOemCode() {
        return oemCode;
    }

    public void setOemCode(String oemCode) {
        this.oemCode = oemCode;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSuitableMachine() {
        return suitableMachine;
    }

    public void setSuitableMachine(String suitableMachine) {
        this.suitableMachine = suitableMachine;
    }

    public String getPageYield() {
        return pageYield;
    }

    public void setPageYield(String pageYield) {
        this.pageYield = pageYield;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }


    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getNewArrival() {
        return newArrival;
    }

    public void setNewArrival(String newArrival) {
        this.newArrival = newArrival;
    }
}
