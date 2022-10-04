package br.com.dnaspecialty.apitest.exception;

import br.com.dnaspecialty.apitest.enumerator.ApplicationMessageEnum;

public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 3099562554978368818L;
    private static final String NOT_FOUND = ApplicationMessageEnum.X0_NOT_FOUND.handleMessage("Registro");

    public NotFoundException() {
        super(NOT_FOUND);
    }

    public NotFoundException(final String message) {
        super(message);
    }

    public NotFoundException(final ApplicationMessageEnum message, final String... arguments) {
        super(message.handleMessage(arguments));
    }

}
