package com.rnd.exception;

import com.rnd.model.dto.ErrorDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;


@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class WebExceptionHandler {

    private static final String NOT_FOUND_MESSAGE_TEMPLATE = "NOT FOUND MESSAGE FOR KEY ";

    private final MessageSource messageSource;

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<ErrorDto> handleServiceError(NotFoundException ex) {
        String message = messageSource.getMessage(ex.getMessageKey(), null, NOT_FOUND_MESSAGE_TEMPLATE + ex.getMessageKey(), LocaleContextHolder.getLocale());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(HttpStatus.BAD_REQUEST.value(), Collections.singleton(message)));
    }

    @ExceptionHandler(value = {ServiceException.class})
    protected ResponseEntity<ErrorDto> handleServiceError(ServiceException ex) {
        String message = messageSource.getMessage(ex.getMessageKey(), null, NOT_FOUND_MESSAGE_TEMPLATE + ex.getMessageKey(), LocaleContextHolder.getLocale());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(HttpStatus.BAD_REQUEST.value(), Collections.singleton(message)));
    }

    @ExceptionHandler(value = {ValidationException.class})
    protected ResponseEntity<ErrorDto> handleServiceError(ValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(HttpStatus.BAD_REQUEST.value(), ex.getErrors()));
    }

}
