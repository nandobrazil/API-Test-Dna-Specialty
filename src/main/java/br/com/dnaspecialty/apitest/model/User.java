package br.com.dnaspecialty.apitest.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends EntityBasic {

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "cpf", length = 100, nullable = false)
    private String cpf;

    @Column(name = "login", length = 100, nullable = false)
    private String login;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Order> order;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Order> getOrder() {
        return order;
    }

    public void setOrder(Set<Order> order) {
        this.order = order;
    }
}
