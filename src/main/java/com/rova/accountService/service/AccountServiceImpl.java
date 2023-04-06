package com.rova.accountService.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.google.gson.Gson;
import com.rova.accountService.dto.RevoResponse;
import com.rova.accountService.dto.CreateAccountRequestDto;
import com.rova.accountService.exception.ResourceNotFoundException;
import com.rova.accountService.model.Account;
import com.rova.accountService.model.AccountType;
import com.rova.accountService.model.User;
import com.rova.accountService.repository.AccountRepository;
import com.rova.accountService.repository.UserRepository;
import com.rova.accountService.util.ResponseHelper;
import com.rova.accountService.util.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import com.rova.accountService.http.HttpClient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.rova.accountService.util.Constants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final UserRepository userRepository;

    private final AccountRepository accountRepository;
    private final ResponseHelper responseHelper;
    private final HttpClient httpClient;
    @Value("${transactionServiceUrl}")
    private String transactionServiceUrl;

    @Value("${sqs.transaction.queue.url}")
    private String sqsUrl;

    private final AmazonSQS amazonSQS;

    private final Gson gson;

    @Override
    public RevoResponse createCurrentAccount(CreateAccountRequestDto request){
        try {

            log.info("Customer ID is: {}", request.getCustomerId());
            //check if user with customerId exist
            Optional<User> checkUserExist = userRepository.findById(Long.valueOf(request.getCustomerId()));
            Optional<User> getuser = userRepository.findById(Long.valueOf(request.getCustomerId()));
            if(!checkUserExist.isPresent()){
                log.info("User with Customer ID {} does not exist", request.getCustomerId());
                throw new ResourceNotFoundException("User with Customer ID does not exist");
            }

            //check if current account has already been opened for this user
            Account checkAccountExist = accountRepository.findByCustomerId(request.getCustomerId());
            if(!Objects.isNull(checkAccountExist))
                throw new ResourceNotFoundException("This user already has a current account");

            Account account = new Account();
            Integer accountNumber = Utility.generateRandomDigits(10);
            account.setAccountNumber(String.valueOf(accountNumber));
            account.setAccountType(AccountType.CURRENT);
            BigDecimal balance = (request.getInitialCredit() == null) ? null : new BigDecimal(request.getInitialCredit());
            account.setBalance(balance);
            account.setCustomerId(request.getCustomerId());
            accountRepository.save(account);

            BigDecimal initialCredit = new BigDecimal(request.getInitialCredit());
            int initialCreditValue = initialCredit.compareTo(BigDecimal.ZERO);

            if(initialCreditValue > 0){
                log.info("Pushing transaction to Message queue");
                String jsonRequest = httpClient.toJson(request);
                log.info("Json Request: {}", jsonRequest);
                SendMessageRequest send_msg_request = new SendMessageRequest()
                        .withQueueUrl(sqsUrl)
                        .withMessageBody(jsonRequest)
                        .withDelaySeconds(5);
                amazonSQS.sendMessage(send_msg_request);
                log.info("Pransaction pushed to Message queue");
                return responseHelper.getResponse(SUCCESS_CODE, SUCCESS, account, HttpStatus.CREATED);
            }
            return responseHelper.getResponse(FAILED_CODE, FAILED, null, HttpStatus.EXPECTATION_FAILED);
        }
        catch (Exception e) {
            return responseHelper.getResponse(FAILED_CODE, FAILED, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    private Map<String, String> getHeader(){
        return Map.of(
                "Content-Type", "application/json; charset=utf-8",
                "Accept", "application/json"
        );
    }
}
