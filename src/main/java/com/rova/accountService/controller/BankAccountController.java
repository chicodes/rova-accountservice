package com.rova.accountService.controller;

import com.rova.accountService.dto.RevoResponse;
import com.rova.accountService.dto.CreateAccountRequestDto;
import com.rova.accountService.service.AccountService;
import com.rova.accountService.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
@RequestMapping(Constants.BASE_URL+"/bankAccount")
public class BankAccountController {

    private final AccountService accountService;
    @PostMapping("")
    public ResponseEntity<RevoResponse> addCurrentAccount(@RequestBody CreateAccountRequestDto request){
        RevoResponse resp = accountService.createCurrentAccount(request);
        return new ResponseEntity<>(resp, resp.getHttpStatus());
    }
}
