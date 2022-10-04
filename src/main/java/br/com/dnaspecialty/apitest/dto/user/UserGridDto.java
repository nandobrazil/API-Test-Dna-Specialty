package br.com.dnaspecialty.apitest.dto.user;

import br.com.dnaspecialty.apitest.model.User;

public class UserGridDto {

    private Long id;

    private String name;

    private String cpf;

    private String login;

    public UserGridDto(
            final Long id,
            final String name,
            final String cpf,
            final String login) {
        this.setId(id);
        this.setName(name);
        this.setCpf(cpf);
        this.setLogin(login);
    }

    public static UserGridDto from(final User user) {
        return new UserGridDto(
            user.getId(),
            user.getName(),
            user.getCpf(),
            user.getLogin());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
