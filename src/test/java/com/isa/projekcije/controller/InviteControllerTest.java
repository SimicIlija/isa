package com.isa.projekcije.controller;

import com.isa.projekcije.ProjekcijeApplicationTests;
import com.isa.projekcije.constants.InviteConstants;
import com.isa.projekcije.model.User;
import com.isa.projekcije.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class InviteControllerTest extends ProjekcijeApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    private static final String BASE_URL = "/invites";
    private static final String DECLINE = "/decline/";
    private static final String CONFIRM = "/confirm/";

    @Test
    @Transactional
    @Rollback(true)
    public void confirmNonExistingInviteShouldReturnNotFound() {
        User user = userService.findById(2L);
        userService.setCurrentUser(user);
        try {
            mockMvc.perform(post(BASE_URL + CONFIRM + InviteConstants.NON_EXISTING_INVITE)).andExpect(status().isNotFound());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    @Rollback(true)
    public void confirmExistingInviteShouldReturnOk() {
        User user = userService.findById(2L);
        userService.setCurrentUser(user);
        try {
            mockMvc.perform(post(BASE_URL + CONFIRM + InviteConstants.EXISTING_INVITE)).andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    @Transactional
    @Rollback(true)
    public void declineNonExistingInviteShouldReturnNotFound() {
        User user = userService.findById(3L);
        userService.setCurrentUser(user);
        try {
            mockMvc.perform(put(BASE_URL + DECLINE + InviteConstants.NON_EXISTING_INVITE)).andExpect(status().isOk())
                    .andExpect(content().contentType(contentType))
                    .andExpect(content().string("false"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    @Rollback(true)
    public void declineExistingInviteShouldReturnOk() {
        User user = userService.findById(2L);
        userService.setCurrentUser(user);
        try {
            mockMvc.perform(put(BASE_URL + DECLINE + InviteConstants.EXISTING_INVITE)).andExpect(status().isOk())
                    .andExpect(content().contentType(contentType))
                    .andExpect(content().string("true"));
            ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
