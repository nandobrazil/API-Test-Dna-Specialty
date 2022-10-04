package br.com.dnaspecialty.apitest.dto.product;

import br.com.dnaspecialty.apitest.model.Product;

public class ProductOptionsDto {
    private final String label;
    private final Long value;

    private final Integer data;

    protected ProductOptionsDto(
            String label,
            Long value,
            Integer data
            ){
        this.label = label;
        this.value = value;
        this.data = data;
    }

    public static ProductOptionsDto of(Product product) {
        return new ProductOptionsDto(
                product.getName(),
                product.getId(),
                product.getPrice()
        );
    }

    public String getLabel() {
        return label;
    }

    public Long getValue() {
        return value;
    }

    public Integer getData() {
        return data;
    }
}
