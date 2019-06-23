package com.rnd.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AccountDto {

    private UUID id;

    private Long amount;
}
