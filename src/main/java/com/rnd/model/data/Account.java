package com.rnd.model.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
    private long amount;
}
