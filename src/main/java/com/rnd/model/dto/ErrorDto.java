package com.rnd.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDto {

    private int status;

    private Set<String> errors = new HashSet<>();

}
