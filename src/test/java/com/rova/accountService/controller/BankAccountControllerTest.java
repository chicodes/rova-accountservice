package com.rova.accountService.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rova.accountService.AccountServiceApplication;
import com.rova.accountService.dto.CreateAccountRequestDto;
import com.rova.accountService.model.Account;
import com.rova.accountService.model.AccountType;
import com.rova.accountService.model.User;
import com.rova.accountService.repository.AccountRepository;
import com.rova.accountService.repository.UserRepository;
import com.rova.accountService.util.Utility;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AccountServiceApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("local")
class BankAccountControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserRepository userRepository;
    @MockBean
    AccountRepository accountRepository;

    @Test
    void addCurrentAccountShouldBeSuccessful() throws Exception {

        User user  = new User();
        user.setId(10l);
        user.setFirstName("");
        user.setLastname("");
        user.setGender("MALE");
        user.setState("Lagos");
        user.setEmail("legend237@rova.com");
        user.setDob("05/02/2000");
        user.setDateCreated(Utility.getCurrentDate());


        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        Account account = new Account();
        Integer accountNumber = Utility.generateRandomDigits(10);
        account.setAccountNumber(String.valueOf(accountNumber));
        account.setAccountType(AccountType.CURRENT);

        BigDecimal balance = BigDecimal.valueOf(10000.000);
        account.setBalance(balance);
        account.setCustomerId(String.valueOf(user.getId()));
        Mockito.when(accountRepository.save(account)).thenReturn(account);


        CreateAccountRequestDto requestDto = new CreateAccountRequestDto();
        requestDto.setInitialCredit("10000");
        requestDto.setCustomerId(String.valueOf(user.getId()));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/bankAccount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.respCode", Matchers.is("00")))
                .andExpect(jsonPath("$.respDescription", Matchers.is("SUCCESS")));
    }


}