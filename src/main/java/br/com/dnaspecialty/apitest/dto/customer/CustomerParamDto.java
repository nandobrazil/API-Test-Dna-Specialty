package br.com.dnaspecialty.apitest.dto.customer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class CustomerParamDto {
    @NotBlank
    private final String corporateName;

    @NotBlank
    private final String cnpj;

    public CustomerParamDto(
            String corporateName,
            String cnpj) {
        this.corporateName = corporateName;
        this.cnpj = cnpj;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public String getCnpj() {
        return cnpj;
    }

}
