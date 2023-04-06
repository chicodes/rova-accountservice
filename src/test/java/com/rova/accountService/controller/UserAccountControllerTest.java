package com.rova.accountService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rova.accountService.AccountServiceApplication;
import com.rova.accountService.dto.CreateAccountRequestDto;
import com.rova.accountService.dto.CreateUserRequestDto;
import com.rova.accountService.dto.RevoResponse;
import com.rova.accountService.model.User;
import com.rova.accountService.repository.AccountRepository;
import com.rova.accountService.repository.UserRepository;
import com.rova.accountService.util.HttpCall;
import com.rova.accountService.util.Utility;
import okhttp3.Request;
import okhttp3.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AccountServiceApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("local")
class UserAccountControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserRepository userRepository;
    @MockBean
    AccountRepository accountRepository;

    @MockBean
    HttpCall httpCall;

    @Test
    void addCurrentAccountShouldBeSuccessful() throws Exception {

        User user = new User();
        user.setId(10l);
        user.setFirstName("Emeka");
        user.setLastname("Onu");
        user.setGender("MALE");
        user.setState("Lagos");
        user.setEmail("legend237@rova.com");
        user.setDob("05/02/2000");
        user.setDateCreated(Utility.getCurrentDate());

        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        Mockito.when(userRepository.save(user)).thenReturn(user);

        CreateUserRequestDto request = new CreateUserRequestDto();
        request.setFirstName("Emmanuel");
        request.setFirstName("Gilga");
        request.setEmail("Giglga@Rova.com");
        request.setAddress("3 medview Lokoja");
        request.setGender("MALE");
        request.setDob("05/08/1996");
        request.setPhone("+234909202484");
        request.setState("Kwara");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.respCode", Matchers.is("00")))
                .andExpect(jsonPath("$.respDescription", Matchers.is("SUCCESS")));
    }


    @Test
    void getUserInfoShouldBeSuccessful() throws Exception {

        CreateUserRequestDto request = new CreateUserRequestDto();
        request.setFirstName("Emmanuel");
        request.setFirstName("Gilga");
        request.setEmail("Giglga@Rova.com");
        request.setAddress("3 medview Lokoja");
        request.setGender("MALE");
        request.setDob("05/08/1996");
        request.setPhone("+234909202484");
        request.setState("Kwara");

        User user = new User();
        user.setId(10l);
        user.setFirstName("Emeka");
        user.setLastname("Onu");
        user.setGender("MALE");
        user.setState("Lagos");
        user.setEmail("legend237@rova.com");
        user.setDob("05/02/2000");
        user.setDateCreated(Utility.getCurrentDate());

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        String json = "{\n" +
                "  \"respBody\" : {\n" +
                "    \"number\" : 0,\n" +
                "    \"last\" : true,\n" +
                "    \"numberOfElements\" : 4,\n" +
                "    \"size\" : 10,\n" +
                "    \"totalPages\" : 1,\n" +
                "    \"pageable\" : {\n" +
                "      \"paged\" : true,\n" +
                "      \"pageNumber\" : 0,\n" +
                "      \"offset\" : 0,\n" +
                "      \"pageSize\" : 10,\n" +
                "      \"unpaged\" : false,\n" +
                "      \"sort\" : {\n" +
                "        \"unsorted\" : true,\n" +
                "        \"sorted\" : false,\n" +
                "        \"empty\" : true\n" +
                "      }\n" +
                "    },\n" +
                "    \"sort\" : {\n" +
                "      \"unsorted\" : true,\n" +
                "      \"sorted\" : false,\n" +
                "      \"empty\" : true\n" +
                "    },\n" +
                "    \"content\" : [ {\n" +
                "      \"transactionType\" : \"DEPOSIT\",\n" +
                "      \"amount\" : 10000.0,\n" +
                "      \"dateCreated\" : \"2023-04-05T06:27:11.000+01:00\",\n" +
                "      \"balanceBefore\" : 0.0,\n" +
                "      \"customerId\" : \"1\",\n" +
                "      \"balanceAfter\" : 10000.0,\n" +
                "      \"id\" : 13,\n" +
                "      \"dateUpdated\" : null\n" +
                "    }, {\n" +
                "      \"transactionType\" : \"DEPOSIT\",\n" +
                "      \"amount\" : 10000.0,\n" +
                "      \"dateCreated\" : \"2023-04-05T06:32:25.000+01:00\",\n" +
                "      \"balanceBefore\" : 10000.0,\n" +
                "      \"customerId\" : \"1\",\n" +
                "      \"balanceAfter\" : 20000.0,\n" +
                "      \"id\" : 15,\n" +
                "      \"dateUpdated\" : null\n" +
                "    }, {\n" +
                "      \"transactionType\" : \"DEPOSIT\",\n" +
                "      \"amount\" : 10000.0,\n" +
                "      \"dateCreated\" : \"2023-04-05T09:13:45.000+01:00\",\n" +
                "      \"balanceBefore\" : 20000.0,\n" +
                "      \"customerId\" : \"1\",\n" +
                "      \"balanceAfter\" : 30000.0,\n" +
                "      \"id\" : 16,\n" +
                "      \"dateUpdated\" : null\n" +
                "    }, {\n" +
                "      \"transactionType\" : \"DEPOSIT\",\n" +
                "      \"amount\" : 345000.0,\n" +
                "      \"dateCreated\" : \"2023-04-05T15:29:21.000+01:00\",\n" +
                "      \"balanceBefore\" : 30000.0,\n" +
                "      \"customerId\" : \"1\",\n" +
                "      \"balanceAfter\" : 375000.0,\n" +
                "      \"id\" : 19,\n" +
                "      \"dateUpdated\" : null\n" +
                "    } ],\n" +
                "    \"first\" : true,\n" +
                "    \"totalElements\" : 4,\n" +
                "    \"empty\" : false\n" +
                "  },\n" +
                "  \"respDescription\" : \"SUCCESS\",\n" +
                "  \"respCode\" : \"00\"\n" +
                "}";



        Map responseMap = new HashMap<>();

        responseMap.put("respBody",json);
        responseMap.put("respDescription", "SUCCESS");
        responseMap.put("respCode", "00");


        Map map = new HashMap<>();
        //map.put("token", "test");

        Response response = null;
        //response.body();
        String jsoString = new ObjectMapper().writeValueAsString(json);
        Mockito.when(httpCall.external("10", map, "http://localhost:3393/api/v1/transaction/customer/10")).thenReturn(response);

        Mockito.when(userRepository.save(user)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/"+user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.respCode", Matchers.is("00")))
                .andExpect(jsonPath("$.respDescription", Matchers.is("SUCCESS")));
    }
}