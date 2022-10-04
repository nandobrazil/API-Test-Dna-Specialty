package br.com.dnaspecialty.apitest.model;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "order", schema = "testdnaspecialty")
public class Order extends EntityBasic {

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_customer")
    private Customer customer;

    @Column(name = "amount")
    private Integer amount;


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Transient
    public Long getCustomerId() {
        return Optional.ofNullable(this.getCustomer())
                .map(EntityBasic::getId)
                .orElse(null);
    }

    @Transient
    public Long getUserId() {
        return Optional.ofNullable(this.getUser())
                .map(EntityBasic::getId)
                .orElse(null);
    }

    @Transient
    public Long getProductId() {
        return Optional.ofNullable(this.getProduct())
                .map(EntityBasic::getId)
                .orElse(null);
    }
}
