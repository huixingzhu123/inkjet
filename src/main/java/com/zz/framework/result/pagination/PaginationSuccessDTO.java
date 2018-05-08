package com.zz.framework.result.pagination;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * SinoGear分页响应结果包装类
 *
 * @author linpeng, YuJian
 * @since 0.6.0
 */
public class PaginationSuccessDTO<T> {

    /**
     * 默认页数从1开始
     */
    private static final int START_PAGE_NUMBER = 1;
    /**
     * Sinogear分页类
     */
    private Pagination pagination;
    /**
     * Sinogear数据集合
     */
    private List<T> data;

    /**
     * @param data 分页数据对象
     */
    public PaginationSuccessDTO(List<T> data) {
        this.pagination = new Pagination(START_PAGE_NUMBER, data.size(), data.size());
        this.data = data;
    }

    /**
     * @param pageData 分页page对象
     */
    public PaginationSuccessDTO(Page<T> pageData) {
        // 页码从START_PAGE_NUMBER(1)开始
        pagination = new Pagination(pageData.getNumber() + START_PAGE_NUMBER, pageData.getSize(),
                pageData.getTotalElements());
        data = pageData.getContent();
    }

    /**
     * @return the pagination
     */
    public Pagination getPagination() {
        return pagination;
    }

    /**
     * @param pagination the pagination to set
     */
    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    /**
     * @return the data
     */
    public List<T> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PaginationSuccessDTO{" +
                "pagination=" + pagination +
                ", data=" + data +
                '}';
    }
}

