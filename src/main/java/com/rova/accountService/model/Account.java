package com.rova.accountService.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String accountNumber;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private String customerId;
    private String userId;
    private BigDecimal balance;
    private Date open_date;
    private Date last_transaction_date;
    private String status;
    private Integer interest_rate;
    private Date dateCreated;
    private Date dateUpdated;

}
