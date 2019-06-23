package com.rnd.service;

import com.rnd.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ValidatorService {

    private final Validator validator;

    public  <T> void validate(T object) {
        Set<String> messages = validator.validate(object).stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
        if (messages.isEmpty()) {
            return;
        }
        throw new ValidationException(messages);
    }
}
