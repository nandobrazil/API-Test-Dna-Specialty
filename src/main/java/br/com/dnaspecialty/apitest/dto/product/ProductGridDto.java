package br.com.dnaspecialty.apitest.dto.product;

import br.com.dnaspecialty.apitest.model.Product;

public class ProductGridDto {

    private final Long id;

    private final String name;

    private final Integer price;

    private final ProductOptionsDto option;

    public ProductGridDto(
            final Long id,
            final String name,
            final Integer price,
            final ProductOptionsDto option) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.option = option;
    }

    public static ProductGridDto from(final Product product) {
        return new ProductGridDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                ProductOptionsDto.of(product));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }


    public ProductOptionsDto getOption() {
        return option;
    }
}
