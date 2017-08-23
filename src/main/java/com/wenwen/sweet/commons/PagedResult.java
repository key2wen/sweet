package com.wenwen.sweet.commons;


import java.util.List;

/**
 * @param <T>
 * @author yunxiang.zhang
 * @date 2016年1月30日
 */
public class PagedResult<T extends BaseBean> extends ToString {
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int DEFAULT_SHOW_PAGES = 7;
    private List<T> list;
    /** 总数 */
    private int total;
    /** 当前页 */
    private Integer pageNum = 1;
    /** 一页显示的条目数 */
    private Integer pageSize = DEFAULT_PAGE_SIZE;
    /** 显示页数 */
    private int showPages;
    /** 最小页码 */
    private int minPage;
    /** 总页数 */
    private int totalPages;
    /** 最大页码 */
    private int maxPage;

    /** SQL排序limit 0,10 */
    private int offset;
    private int limit;

    public PagedResult() {
    }

    public PagedResult(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        adjustAndCalOffset();
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
        calPagination();
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public int getShowPages() {
        return showPages;
    }

    public void setShowPages(int showPages) {
        this.showPages = showPages;
    }

    public int getMinPage() {
        return minPage;
    }

    public void setMinPage(int minPage) {
        this.minPage = minPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public void calLimitOffset() {
        offset = (pageNum - 1) * pageSize;
        limit = pageSize;
    }

    public void calPagination() {
        totalPages = total / pageSize;
        if (total % pageSize != 0) {
            totalPages++;
        }
        if (totalPages > DEFAULT_SHOW_PAGES) {
            showPages = DEFAULT_SHOW_PAGES;
        } else {
            showPages = totalPages;
        }

        // 最小页码,如果小于1,则设为1
        minPage = pageNum - showPages / 2;
        minPage = minPage < 1 ? 1 : minPage;
        maxPage = minPage + showPages - 1;
        if (maxPage > totalPages) {
            maxPage = totalPages;
        }
        maxPage = maxPage < 1 ? 1 : maxPage;
    }

    public void adjustAndCalOffset() {
        if (pageSize == null || pageSize == 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        calLimitOffset();
    }
}
