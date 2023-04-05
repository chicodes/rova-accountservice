package com.rova.accountService.controller;

import com.rova.accountService.dto.RevoResponse;
import com.rova.accountService.dto.CreateUserRequestDto;
import com.rova.accountService.service.UserService;
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
@RequestMapping(Constants.BASE_URL+"/user")
public class UserAccountController {

    private final UserService createUser;
    @PostMapping("")
    public ResponseEntity<RevoResponse> addUser(@RequestBody CreateUserRequestDto request){
        RevoResponse resp = createUser.createUser(request);
        return new ResponseEntity<>(resp, resp.getHttpStatus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RevoResponse> getUserInfo(@PathVariable String id){
        RevoResponse resp = createUser.getUserInfo(id);
        return new ResponseEntity<>(resp, resp.getHttpStatus());
    }
}
