package br.com.dnaspecialty.apitest.dto.order;

import br.com.dnaspecialty.apitest.model.Order;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class OrderParamDto {
    @NotBlank
    private final Long idProduct;

    @NotBlank
    private final Long idUser;

    @NotBlank
    private final Long idCustomer;

    @NotBlank
    private final Integer amount;


    protected OrderParamDto(
            Long idProduct,
            Long idUser,
            Long idCustomer,
            Integer amount) {
        this.idCustomer = idCustomer;
        this.idUser = idUser;
        this.idProduct = idProduct;
        this.amount = amount;
    }

    public static OrderParamDto of(Order order) {
        return new OrderParamDto(
                order.getCustomerId(),
                order.getUserId(),
                order.getProductId(),
                order.getAmount()
        );
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public Long getIdUser() {
        return idUser;
    }

    public Long getIdCustomer() {
        return idCustomer;
    }

    public Integer getAmount() {
        return amount;
    }

}
