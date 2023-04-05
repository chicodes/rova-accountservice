package com.rova.accountService.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.rova.accountService.dto.GetUserResponse;
import com.rova.accountService.dto.RevoResponse;
import com.rova.accountService.dto.CreateUserRequestDto;
import com.rova.accountService.dto.transactionResponse.TransactionResponseDto;
import com.rova.accountService.exception.RemoteServiceException;
import com.rova.accountService.exception.ResourceNotFoundException;
import com.rova.accountService.http.HttpClient;
import com.rova.accountService.model.User;
import com.rova.accountService.repository.UserRepository;
import com.rova.accountService.util.HttpCall;
import com.rova.accountService.util.ResponseHelper;
import com.rova.accountService.util.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.rova.accountService.util.Constants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ResponseHelper responseHelper;
    private final HttpCall httpCall;

    @Value("${transactionServiceUrl}")
    private String transactionServiceUrl;

    private final HttpClient httpClient;
    private final Gson gson;

    @Override
    public RevoResponse createUser(CreateUserRequestDto request){
        try {

            User checkUserExist = userRepository.findByEmail(request.getEmail());
            if(!Objects.isNull(checkUserExist))
                throw  new RemoteServiceException("User with email already exist");

            User user = new User();
            user.setFirstName(request.getFirstName());
            user.setLastname(request.getLastname());
            user.setPhone(request.getPhone());
            user.setEmail(request.getEmail());
            user.setAddress(request.getAddress());
            user.setState(request.getState());
            user.setGender(request.getGender());
            user.setDob(request.getDob());
            user.setDateCreated(Utility.getCurrentDate());
            user.setLastname(request.getLastname());
            user.setLastname(request.getLastname());
            user.setLastname(request.getLastname());
            userRepository.save(user);
            return responseHelper.getResponse(SUCCESS_CODE, SUCCESS, user, HttpStatus.CREATED);
        }
        catch (Exception e) {
            return responseHelper.getResponse(FAILED_CODE, FAILED, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public RevoResponse getUserInfo(String id){

        try {
            Optional<User> getuser = userRepository.findById(Long.valueOf(id));
            if (!getuser.isPresent())
                throw new ResourceNotFoundException("User not found");

            GetUserResponse getUserResponse = new GetUserResponse();
            getUserResponse.setUser(getuser);
            Map map = new HashMap<>();
            map.put("token", "test");
            String url = transactionServiceUrl + "/customer/" + id;
            log.info("URL: {}", url);
            String response = httpCall.external(id, map, url);
            log.info("Plain Transaction Response: {}", response);
            TransactionResponseDto transactionResponseDto = gson.fromJson(response, TransactionResponseDto.class);
            ObjectMapper objectMapper = new ObjectMapper();
            log.info("Transaction Response: {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(transactionResponseDto));
            if (transactionResponseDto.getRespCode().equals("00")){
                getUserResponse.setTransactions(transactionResponseDto.getRespBody().getContent());
                return responseHelper.getResponse(SUCCESS_CODE, SUCCESS, getUserResponse, HttpStatus.OK);
            }
            return responseHelper.getResponse(FAILED_CODE, FAILED, null, HttpStatus.EXPECTATION_FAILED);
        }
        catch (Exception e){
            return responseHelper.getResponse(FAILED_CODE, FAILED, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }

    }
}
