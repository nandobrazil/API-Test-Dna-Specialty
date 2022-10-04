package br.com.dnaspecialty.apitest.configuration;

import br.com.dnaspecialty.apitest.dto.commons.ErrorDto;
import br.com.dnaspecialty.apitest.dto.commons.FormValidationErrorDto;
import br.com.dnaspecialty.apitest.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ResourceExceptionHandler {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<FormValidationErrorDto> handle(final MethodArgumentNotValidException exception) {
        final List<FormValidationErrorDto> errors = new ArrayList<>();
        final List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        fieldErrors.forEach(error -> {
            final String message = error.getDefaultMessage();
            final FormValidationErrorDto formValidationErrorDto = new FormValidationErrorDto(error.getField(), message);

            errors.add(formValidationErrorDto);
        });

        return errors;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorDto handle(final IllegalArgumentException exception) {
        return new ErrorDto(exception.getMessage());
    }

    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({BusinessException.class, GenerateHashException.class})
    public ErrorDto handle(final BusinessException exception) {
        return new ErrorDto(exception.getMessage());
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorDto handle(final NotFoundException exception) {
        return new ErrorDto(exception.getMessage());
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(GenerateFailureException.class)
    public ErrorDto handle(final GenerateFailureException exception) {
        return new ErrorDto(exception.getMessage());
    }

    @ResponseStatus(code = HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(GatewayException.class)
    public ErrorDto handle(final GatewayException exception) {
        return new ErrorDto(exception.getMessage());
    }
}
