package br.com.dnaspecialty.apitest.dto.user;

import br.com.dnaspecialty.apitest.model.User;

public class UserOptionsDto {
    private final String label;
    private final Long value;
    private final String data;

    protected UserOptionsDto(
            String label,
            Long value,
            String data
            ){
        this.label = label;
        this.value = value;
        this.data = data;
    }

    public static UserOptionsDto of(User user) {
        return new UserOptionsDto(
                user.getName(),
                user.getId(),
                user.getCpf()
        );
    }

    public String getLabel() {
        return label;
    }

    public Long getValue() {
        return value;
    }

    public String getData() {
        return data;
    }
}
