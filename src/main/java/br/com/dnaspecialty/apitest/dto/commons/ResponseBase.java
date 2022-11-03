package br.com.dnaspecialty.apitest.dto.commons;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Model responsible for encapsulating all api responses.")
@JsonPropertyOrder({"success", "message", "data", "pagination"})
public class ResponseBase<T> {

    @ApiModelProperty(position = 2)
    private String message;

    @ApiModelProperty(position = 1)
    private boolean success = true;

    @ApiModelProperty(position = 3)
    private T data;

    public static <T> ResponseBase<T> of(final T data) {
        final ResponseBase<T> response = new ResponseBase<T>();
        response.setData(data);
        response.setSuccess(true);
        return response;
    }

    public static <T> ResponseBase<T> empty() {
        final ResponseBase<T> response = new ResponseBase<T>();
        response.setSuccess(true);
        return response;
    }

    public String getMessage() {
        return this.message;
    }

    public ResponseBase<T> setMessage(final String message) {
        this.message = message;
        return this;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public ResponseBase<T> setSuccess(final boolean success) {
        this.success = success;
        return this;
    }

    public T getData() {
        return this.data;
    }

    public ResponseBase<T> setData(final T entity) {
        this.data = entity;
        return this;
    }
}
