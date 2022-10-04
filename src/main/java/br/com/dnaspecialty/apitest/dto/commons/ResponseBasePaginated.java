package br.com.dnaspecialty.apitest.dto.commons;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.domain.Page;

import java.util.List;


@ApiModel(description = "Model responsible for encapsulating all paged responses from the api.")
@JsonPropertyOrder({"success", "message", "data", "pagination"})
public class ResponseBasePaginated<T> extends ResponseBase<T> {

    @ApiModelProperty(position = 2)
    private String message;

    @ApiModelProperty(position = 1)
    private boolean success = true;

    @ApiModelProperty(position = 3)
    private T data;

    @ApiModelProperty(position = 4)
    private PaginatedBase pagination;

    public static <T> ResponseBasePaginated<List<T>> ofPage(final Page<T> page) {
        final PaginatedBase paginatedBase = new PaginatedBase();
        paginatedBase.setPage(page.getNumber() + 1);
        paginatedBase.setPageSize(page.getSize());
        paginatedBase.setTotalPages(page.getTotalPages());
        paginatedBase.setTotalRecords((int) page.getTotalElements());

        final ResponseBasePaginated<List<T>> responseBasePaginated = new ResponseBasePaginated<>();
        responseBasePaginated.setData(page.getContent());
        responseBasePaginated.setSuccess(true);
        responseBasePaginated.setPagination(paginatedBase);

        return responseBasePaginated;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public ResponseBasePaginated<T> setMessage(final String message) {
        this.message = message;
        return this;
    }

    @Override
    public boolean isSuccess() {
        return this.success;
    }

    @Override
    public ResponseBasePaginated<T> setSuccess(final boolean success) {
        this.success = success;
        return this;
    }

    @Override
    public T getData() {
        return this.data;
    }

    @Override
    public ResponseBasePaginated<T> setData(final T entity) {
        this.data = entity;
        return this;
    }

    public PaginatedBase getPagination() {
        return this.pagination;
    }

    public ResponseBasePaginated<T> setPagination(final PaginatedBase pagination) {
        this.pagination = pagination;
        return this;
    }

}
