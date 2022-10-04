package br.com.dnaspecialty.apitest.exception;

import br.com.dnaspecialty.apitest.enumerator.ApplicationMessageEnum;

public class GenerateFailureException extends RuntimeException {

    private static final long serialVersionUID = 3099562132978368868L;
    private static final String GENERATION_FAILURE = ApplicationMessageEnum.GENERATION_FAILURE.handleMessage("Arquivo");

    public GenerateFailureException() {
        super(GENERATION_FAILURE);
    }

    public GenerateFailureException(final String mensage) {
        super(mensage);
    }

    public GenerateFailureException(final ApplicationMessageEnum message, final String... arguments) {
        super(message.handleMessage(arguments));
    }

}
