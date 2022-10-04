package br.com.dnaspecialty.apitest.enumerator;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public enum ApplicationMessageEnum {
    ALREADY_EXISTS("x0.alreadyExists.x1"),
    X0_NOT_FOUND("x0.notFound"),
    GENERATION_FAILURE("generationFailure.x0"),
    BUSINESS_ERROR("business.error");

    private final String value;

    ApplicationMessageEnum(final String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public String handleMessage(final String... args) {
        return this.handleMessage((Object[]) args);
    }

    public String handleMessage(final Object... args) {
        final ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());
        final String message = bundle.getString(this.value);
        return MessageFormat.format(message, args);
    }

    public String handleMessage() {
        final ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());
        return bundle.getString(this.value);
    }
}
