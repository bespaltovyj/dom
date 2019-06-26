package com.rnd.model.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ExchangeAmountsRequestDto {
    private UUID from;
    private UUID to;
    private Long step;
}
