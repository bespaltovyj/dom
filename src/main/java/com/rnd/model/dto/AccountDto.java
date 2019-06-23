package com.rnd.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.UUID;

@Getter
@Setter
public class AccountDto {

    private UUID id;

    @NotNull(message = "{validation.amount_is_not_null}")
    @PositiveOrZero(message = "{validation.amount_is_not_negative}")
    private Long amount = 0L;
}
