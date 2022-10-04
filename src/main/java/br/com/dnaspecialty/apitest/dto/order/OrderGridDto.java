package br.com.dnaspecialty.apitest.dto.order;

import br.com.dnaspecialty.apitest.dto.customer.CustomerGridDto;
import br.com.dnaspecialty.apitest.dto.product.ProductGridDto;
import br.com.dnaspecialty.apitest.dto.user.UserGridDto;
import br.com.dnaspecialty.apitest.model.Order;

public class OrderGridDto {

    private Long id;
    private Integer quantity;
    private Integer amount;
    private ProductGridDto product;
    private UserGridDto user;
    private CustomerGridDto customer;


    public OrderGridDto(
            final Long id,
            final ProductGridDto product,
            final UserGridDto user,
            final CustomerGridDto customer,
            final Integer quantity
    ) {
            this.setId(id);
            this.setProduct(product);
            this.setUser(user);
            this.setCustomer(customer);
            this.setQuantity(quantity);
            this.setAmount(this.getQuantity() * product.getPrice());
    }

    public static OrderGridDto from(final Order order) {
        return new OrderGridDto(
                order.getId(),
                ProductGridDto.from(order.getProduct()),
                UserGridDto.from(order.getUser()),
                CustomerGridDto.from(order.getCustomer()),
                order.getAmount());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductGridDto getProduct() {
        return product;
    }

    public void setProduct(ProductGridDto product) {
        this.product = product;
    }

    public UserGridDto getUser() {
        return user;
    }

    public void setUser(UserGridDto user) {
        this.user = user;
    }

    public CustomerGridDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerGridDto customer) {
        this.customer = customer;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
