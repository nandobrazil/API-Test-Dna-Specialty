package br.com.dnaspecialty.apitest.dto.commons;

import br.com.dnaspecialty.apitest.model.EntityBasic;

public class DeleteResponseDto {
    private final Long id;

    public DeleteResponseDto(final Long id) {
        this.id = id;
    }

    public DeleteResponseDto(final EntityBasic entity) {
        this.id = entity.getId();
    }

    public Long getId() {
        return this.id;
    }
}