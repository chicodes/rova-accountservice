package com.rova.accountService.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CreateUserRequestDto {
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
