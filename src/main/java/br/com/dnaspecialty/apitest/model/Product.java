package br.com.dnaspecialty.apitest.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "product", schema = "testdnaspecialty")
public class Product extends EntityBasic {

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "price", length = 100, nullable = false)
    private Integer price;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<Order> order;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Set<Order> getOrder() {
        return order;
    }

    public void setOrder(Set<Order> order) {
        this.order = order;
    }
}
