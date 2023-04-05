package com.rova.accountService.dto;


import lombok.Data;

@Data
public class CreateAccountRequestDto {
    private String customerId;
    private String initialCredit;
}
