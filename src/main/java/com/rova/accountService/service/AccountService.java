package com.rova.accountService.service;

import com.rova.accountService.dto.RevoResponse;
import com.rova.accountService.dto.CreateAccountRequestDto;

public interface AccountService {

    RevoResponse createCurrentAccount(CreateAccountRequestDto request);
}
