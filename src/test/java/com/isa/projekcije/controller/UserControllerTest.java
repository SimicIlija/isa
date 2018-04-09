package com.isa.projekcije.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.projekcije.ProjekcijeApplicationTests;
import com.isa.projekcije.model.dto.LoginDTO;
import com.isa.projekcije.model.dto.RegistrationDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


public class UserControllerTest extends ProjekcijeApplicationTests {

    @Autowired
    WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    private static final String BASE_URL = "/user";
    private static final String REGISTER = "/registerUser";
    private static final String LOGIN = "/login";

    @Test
    public void signupWithMandatoryFieldEmptyShouldReturnBadRequest1() throws Exception {
        final RegistrationDTO registerDTO = new RegistrationDTO("Marko", "Markovic", "021/858-988", "marko.markovic@gmail.com", "newPassword", "");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(registerDTO);

        mockMvc.perform(post(BASE_URL + REGISTER)
                .contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest());


    }

    @Test
    public void signupWithMandatoryFieldEmptyShouldReturnBadRequest2() throws Exception {
        final RegistrationDTO registerDTO = new RegistrationDTO("Marko", "Markovic", "021/858-988", "", "newPassword", "");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(registerDTO);

        ResultActions res = mockMvc.perform(post(BASE_URL + REGISTER)
                .contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest());
        res.andDo(MockMvcResultHandlers.print());

    }


    @Test
    public void signupWithUnmatchedPasswordsShouldReturnBadRequest() throws Exception {
        final RegistrationDTO registerDTO =
                new RegistrationDTO("Marko", "Markovic", "021/858-988", "marko.markovic@gmail.com", "newPassword", "newPassword11");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(registerDTO);

        ResultActions res = mockMvc.perform(post(BASE_URL + REGISTER)
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
        res.andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void registerWithValidDataShouldReturnCreated() throws Exception {
        final RegistrationDTO registerDTO =
                new RegistrationDTO("Marko", "Markovic", "021/858-988", "jelenastanarevic@gmail.com", "markoo", "markoo");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(registerDTO);

        ResultActions res = mockMvc.perform(post(BASE_URL + REGISTER)
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated());
        res.andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void loginWithValidCredentialsShouldReturnOk() throws Exception {
        final LoginDTO loginDto = new LoginDTO("jelena@gmail.com", "jelena");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(loginDto);

        mockMvc.perform(post(BASE_URL + LOGIN)
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void loginWithInvalidCredentialsShouldReturnNotFound() throws Exception {
        final LoginDTO loginDto = new LoginDTO("jelena@gmail.com", "wrongPassword");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(loginDto);

        mockMvc.perform(post(BASE_URL + LOGIN)
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isNotFound());
    }



}
