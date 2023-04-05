package com.rova.accountService.util;

import com.google.gson.Gson;
import com.rova.accountService.dto.GetUserResponse;
import com.rova.accountService.dto.RevoResponse;
import com.rova.accountService.dto.transactionResponse.TransactionResponseDto;
import com.rova.accountService.exception.GeneralException;
import com.rova.accountService.exception.ResourceNotFoundException;
import com.rova.accountService.http.HttpClient;
import com.rova.accountService.model.User;
import com.rova.accountService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.rova.accountService.util.Constants.*;
import static com.rova.accountService.util.Constants.FAILED;


@Slf4j
@RequiredArgsConstructor
@Service
public class HttpCall {

    private final HttpClient httpClient;

    public String external(String id, Map map, String url){
        try {

            Response response = httpClient.get(getHeader(), map, url);
            return response.body().string();
        }
        catch (Exception e){
            throw new GeneralException("");
        }
    }

    private Map<String, String> getHeader(){
        return Map.of(
                "Content-Type", "application/json; charset=utf-8",
                "Accept", "application/json"
        );
    }
}
