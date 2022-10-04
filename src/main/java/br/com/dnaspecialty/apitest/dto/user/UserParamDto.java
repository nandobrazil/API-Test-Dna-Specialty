package br.com.dnaspecialty.apitest.dto.user;

import br.com.dnaspecialty.apitest.model.User;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class UserParamDto {

    @NotBlank
    private String name;

    @NotBlank
    private String cpf;

    @NotBlank
    private String login;

    @NotBlank
    private String password;

    public UserParamDto() {
    }

    @JsonCreator
    public UserParamDto(
            @JsonProperty("name") String name,
            @JsonProperty("cpf") String cpf,
            @JsonProperty("login") String login,
            @JsonProperty("password") String password) {
        this.setName(name);
        this.setCpf(cpf);
        this.setLogin(login);
        this.setPassword(password);
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
