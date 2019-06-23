package com.rnd.exception;

import com.rnd.model.dto.ErrorDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class WebExceptionHandler {

    @ExceptionHandler(value = {ValidationException.class})
    protected ResponseEntity<ErrorDto> handleServiceError(ValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(HttpStatus.BAD_REQUEST.value(), ex.getErrors()));
    }
}
