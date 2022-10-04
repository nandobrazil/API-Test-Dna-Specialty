package br.com.dnaspecialty.apitest.exception;

import br.com.dnaspecialty.apitest.enumerator.ApplicationMessageEnum;

public class GatewayException extends RuntimeException {

    private static final long serialVersionUID = 3228949821710114160L;
    private static final String BUSINESS_ERROR = ApplicationMessageEnum.BUSINESS_ERROR.handleMessage();

    public GatewayException() {
        super(BUSINESS_ERROR);
    }

    public GatewayException(final String mensage) {
        super(mensage);
    }

    public GatewayException(final ApplicationMessageEnum message, final Object... arguments) {
        super(message.handleMessage(arguments));
    }

}
