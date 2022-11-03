package br.com.dnaspecialty.apitest.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "customer")
public class Customer extends EntityBasic {

    @Column(name = "corporate_name", length = 100, nullable = false)
    private String corporateName;

    @Column(name = "cnpj", length = 14, nullable = false)
    private String cnpj;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<Order> order;

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

    public Set<Order> getOrder() {
        return order;
    }

    public void setOrder(Set<Order> order) {
        this.order = order;
    }
}
