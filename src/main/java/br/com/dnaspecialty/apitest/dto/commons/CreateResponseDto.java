package br.com.dnaspecialty.apitest.dto.commons;

import br.com.dnaspecialty.apitest.model.EntityBasic;

public class CreateResponseDto {
    private Long id;

    public CreateResponseDto(final Long id) {
        this.id = id;
    }

    public CreateResponseDto(final EntityBasic entity) {
        this.id = entity.getId();
    }

    public long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }
}
