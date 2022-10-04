package br.com.dnaspecialty.apitest.dto.commons;

import java.io.Serializable;

public class PaginatedBase implements Serializable {

    private static final long serialVersionUID = -2628613421055717528L;
    private int page;
    private int pageSize;
    private int totalPages;
    private int totalRecords;

    public int getPage() {
        return this.page;
    }

    public void setPage(final int page) {
        this.page = page;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(final int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalRecords() {
        return this.totalRecords;
    }

    public void setTotalRecords(final int totalRecords) {
        this.totalRecords = totalRecords;
    }

}
