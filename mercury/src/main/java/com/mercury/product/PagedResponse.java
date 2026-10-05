package com.mercury.product;

import java.util.List;

public class PagedResponse<T> {
    private List<T> content;
    private Integer pageNumber;
    private Integer totalPages;
    private Integer pageSize;
    private Long totalElements;
    private Boolean first;
    private Boolean last;

    public PagedResponse() {}

    public void setContent(List<T> content) {
        this.content = content;
    }
    public List<T> getContent() {
        return this.content;
    }
    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
    public Integer getPageNumber() {
        return this.pageNumber;
    }
    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
    public Integer getTotalPages() {
        return this.totalPages;
    }
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    public Integer getPageSize() {
        return  this.pageSize;
    }
    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }
    public Long getTotalElements() {
        return this.totalElements;
    }
    public void setIsFirst(Boolean first) {
        this.first = first;
    }
    public Boolean getIsFirst() {
        return this.first;
    }
    public void setIsLast(Boolean last) {
        this.last = last;
    }
    public Boolean getIsLast() {
        return this.last;
    }
}
