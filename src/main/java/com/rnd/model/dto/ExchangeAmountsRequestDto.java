package com.rnd.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeAmountsRequestDto {
    private UUID from;
    private UUID to;
    private Long step;
}
