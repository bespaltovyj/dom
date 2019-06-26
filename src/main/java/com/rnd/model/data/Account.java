package com.rnd.model.data;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "account")
public class Account implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    @PositiveOrZero
    private long amount;

    @Version
    @Setter(AccessLevel.NONE)
    private long version;
}
