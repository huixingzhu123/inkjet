package com.zz.framework.result.pagination;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 分页模型类。
 * <p>
 * 分页以1开始，默认页长为10，默认最大页长100。<br>
 * 用于
 * 1. 从URL中获取并组装分页的查询参数；
 * 2. 与{@link PaginationSuccessDTO}组合使用返回带分页的响应对象。
 *
 * @author linpeng, YuJian
 * @since 0.6.0
 */
public class Pagination {

    /**
     * 默认的分页大小
     */
    private static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 最大的分页大小
     */
    private static final int MAX_PAGE_SIZE = 100;

    /**
     * 最小页数
     */
    private static final int MIN_PAGE = 1;
    /**
     * 每页最小尺寸
     */
    private static final int MIN_PAGE_SIZE = 1;

    /**
     * 页数,首页page=1
     */
    @Min(value = MIN_PAGE, message = "page不能小于1")
    private int page;

    /**
     * 每页记录
     */
    @Min(value = MIN_PAGE_SIZE, message = "pageSize不能小于1")
    @Max(value = MAX_PAGE_SIZE, message = "pageSize不能大于100")
    private int pageSize;

    /**
     * 记录总数。当值为{@link Long#MIN_VALUE}时，表示纪录总数无效，应该忽略处理。
     */
    private long total;

    /**
     * 默认构造函数
     */
    public Pagination() {
        this.page = 1;
        this.pageSize = DEFAULT_PAGE_SIZE;
        this.total = Long.MIN_VALUE;
    }

    /**
     * @param page     页数
     * @param pageSize 每页记录数
     */
    public Pagination(int page, int pageSize) {
        this(page, pageSize, Long.MIN_VALUE);
    }

    /**
     * @param page     页数
     * @param pageSize 每页记录数
     * @param total    总数
     */
    public Pagination(int page, int pageSize, long total) {
        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
    }

    /**
     * @return the page
     */
    public int getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the total
     */
    public long getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(long total) {
        this.total = total;
    }

}
