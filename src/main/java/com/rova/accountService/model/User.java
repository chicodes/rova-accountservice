package com.rova.accountService.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String firstName;
    private String lastname;
    private String phone;
    private String email;
    private String address;
    private String state;
    private String gender;
    private String dob;
    private Date dateCreated;
    private Date dateUpdated;
}
