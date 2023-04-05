package com.rova.accountService.service;

import com.rova.accountService.dto.RevoResponse;
import com.rova.accountService.dto.CreateUserRequestDto;

public interface UserService {

    RevoResponse createUser(CreateUserRequestDto request);

    RevoResponse getUserInfo(String id);
}
