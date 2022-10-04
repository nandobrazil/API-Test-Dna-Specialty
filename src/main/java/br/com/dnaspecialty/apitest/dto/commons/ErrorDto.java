package br.com.dnaspecialty.apitest.dto.commons;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorDto {

    private final String error;
    private final Boolean success;

    public ErrorDto(final String error) {
        this.error = error;
        this.success = false;
    }

    @JsonCreator
    public ErrorDto(
            @JsonProperty("error") final String error,
            @JsonProperty("success") final Boolean success
    ) {
        this.error = error;
        this.success = success;
    }

    public ErrorDto() {
        this.error = null;
        this.success = null;
    }

    public String getError() {
        return this.error;
    }

    public Boolean getSuccess() {
        return this.success;
    }
}
