package com.rnd.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ValidationException extends RuntimeException {

    private Set<String> errors = new HashSet<>();

}
