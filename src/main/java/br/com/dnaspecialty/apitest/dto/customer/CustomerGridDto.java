package br.com.dnaspecialty.apitest.dto.customer;

import br.com.dnaspecialty.apitest.dto.product.ProductGridDto;
import br.com.dnaspecialty.apitest.model.Customer;
import br.com.dnaspecialty.apitest.model.Product;

public class CustomerGridDto {
    private Long id;

    private String corporateName;

    private String cnpj;

    public CustomerGridDto(
            final Long id,
            final String corporateName,
            final String cnpj) {
        this.setId(id);
        this.setCorporateName(corporateName);
        this.setCnpj(cnpj);
    }

    public static CustomerGridDto from(final Customer customer) {
        return new CustomerGridDto(
            customer.getId(),
            customer.getCorporateName(),
            customer.getCnpj());
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
