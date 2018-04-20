package com.isa.projekcije.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.projekcije.ProjekcijeApplicationTests;
import com.isa.projekcije.constants.ActorConstants;
import com.isa.projekcije.model.dto.ActorDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class ActorControllerTest extends ProjekcijeApplicationTests {

    private static final String URL_PREFIX = "/actors";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetByShow() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/getByShow/" + ActorConstants.DB_ID_SHOW))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(ActorConstants.DB_BY_SHOW_COUNT)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(ActorConstants.DB_ID.intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(ActorConstants.DB_NAME)))
                .andExpect(jsonPath("$.[*].lastname").value(hasItem(ActorConstants.DB_LASTNAME)))
                .andExpect(jsonPath("$.[*].idShow").value(hasItem(ActorConstants.DB_ID_SHOW.intValue())));
    }

    @Test
    @WithMockUser(authorities = "ADMIN_INST")
    @Transactional
    @Rollback(true)
    public void testAddActor() throws Exception {
        ActorDTO actorDTO = new ActorDTO();
        actorDTO.setName(ActorConstants.NEW_NAME);
        actorDTO.setLastname(ActorConstants.NEW_LASTNAME);
        actorDTO.setIdShow(ActorConstants.DB_ID_SHOW);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(actorDTO);

        mockMvc.perform(post(URL_PREFIX + "/addActor").contentType(contentType).content(json)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN_INST")
    @Transactional
    @Rollback(true)
    public void testEditActor() throws Exception {
        ActorDTO actorDTO = new ActorDTO();
        actorDTO.setName(ActorConstants.NEW_NAME);
        actorDTO.setLastname(ActorConstants.NEW_LASTNAME);
        actorDTO.setIdShow(ActorConstants.DB_ID_SHOW);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(actorDTO);

        mockMvc.perform(put(URL_PREFIX + "/editActor/" + ActorConstants.DB_ID).contentType(contentType).content(json)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN_INST")
    @Transactional
    @Rollback(true)
    public void testDeleteActorOK() throws Exception {

        mockMvc.perform(delete(URL_PREFIX + "/deleteActor/" + ActorConstants.DB_ID)).andExpect(status().isOk());
    }

}
