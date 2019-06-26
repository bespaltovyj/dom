package com.rnd.exception;

import com.rnd.constants.ErrorMessageKey;
import com.rnd.model.dto.ErrorDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;


@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class WebExceptionHandler {

    private static final String NOT_FOUND_MESSAGE_TEMPLATE = "NOT FOUND MESSAGE FOR KEY ";
    private static final String UNKNOWN_ERROR_MESSAGE_KEY = "error.unknown";

    private final MessageSource messageSource;

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<ErrorDto> handleServiceError(NotFoundException ex) {
        String message = getMessage(ex.getMessageKey(), ex.getArgs());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(HttpStatus.BAD_REQUEST.value(), Collections.singleton(message)));
    }

    @ExceptionHandler(value = {ServiceException.class})
    protected ResponseEntity<ErrorDto> handleServiceError(ServiceException ex) {
        String message = getMessage(ex.getMessageKey(), ex.getArgs());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(HttpStatus.BAD_REQUEST.value(), Collections.singleton(message)));
    }

    @ExceptionHandler(value = {ValidationException.class})
    protected ResponseEntity<ErrorDto> handleServiceError(ValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(HttpStatus.BAD_REQUEST.value(), ex.getErrors()));
    }

    @ExceptionHandler(value = {ObjectOptimisticLockingFailureException.class})
    protected ResponseEntity<ErrorDto> handleServiceError(ObjectOptimisticLockingFailureException ex) {
        String message = getMessage(ErrorMessageKey.OPTIMISTIC_LOCK_EXCEPTION, null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(HttpStatus.BAD_REQUEST.value(), Collections.singleton(message)));
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ErrorDto> handleServiceError(Exception ex) {
        log.error(ex.getMessage());
        ErrorDto error = new ErrorDto(HttpStatus.BAD_REQUEST.value(), Collections.singleton(getMessage(UNKNOWN_ERROR_MESSAGE_KEY, null)));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    private String getMessage(String key, Object[] args) {
        return messageSource.getMessage(key, args, NOT_FOUND_MESSAGE_TEMPLATE + key, LocaleContextHolder.getLocale());
    }

}
