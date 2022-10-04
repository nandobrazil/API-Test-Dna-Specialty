package br.com.dnaspecialty.apitest.exception;

import br.com.dnaspecialty.apitest.enumerator.ApplicationMessageEnum;

import java.text.MessageFormat;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 3228949821710114160L;

    private static final String BUSINESS = ApplicationMessageEnum.BUSINESS_ERROR.handleMessage();

    public BusinessException() {
        super(BUSINESS);
    }

    public BusinessException(final String message) {
        super(message);
    }

    public BusinessException(
            final ApplicationMessageEnum message,
            final Object... arguments
    ) {
        super(message.handleMessage(arguments));
    }

    public BusinessException(
            final String message,
            final Object... arguments
    ) {
        super(MessageFormat.format(message, arguments));
    }

}
