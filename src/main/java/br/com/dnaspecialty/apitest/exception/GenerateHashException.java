package br.com.dnaspecialty.apitest.exception;

import br.com.dnaspecialty.apitest.enumerator.ApplicationMessageEnum;

public class GenerateHashException extends BusinessException {

    private static final long serialVersionUID = 3228949821710114160L;

    public GenerateHashException(final String message) {
        super(message);
    }

    public GenerateHashException(final ApplicationMessageEnum message, final Object... arguments) {
        super(message, arguments);
    }

    public GenerateHashException(final String message, final Object... arguments) {
        super(message, arguments);
    }
}
