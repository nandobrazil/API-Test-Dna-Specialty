package br.com.dnaspecialty.apitest.dto.customer;

import br.com.dnaspecialty.apitest.model.Customer;
import br.com.dnaspecialty.apitest.model.Product;

public class CustomerOptionsDto {
    private final String label;
    private final Long value;
    private final String data;

    protected CustomerOptionsDto(
            String label,
            Long value,
            String data
            ){
        this.label = label;
        this.value = value;
        this.data = data;
    }

    public static CustomerOptionsDto of(Customer customer) {
        return new CustomerOptionsDto(
                customer.getCorporateName(),
                customer.getId(),
                customer.getCnpj()
        );
    }

    public String getLabel() {
        return label;
    }

    public Long getValue() {
        return value;
    }

    public String getData() {
        return data;
    }
}
