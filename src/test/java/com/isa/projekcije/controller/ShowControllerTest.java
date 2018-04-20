package com.isa.projekcije.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.projekcije.ProjekcijeApplicationTests;
import com.isa.projekcije.constants.ShowConstants;
import com.isa.projekcije.model.dto.ShowActorDTO;
import com.isa.projekcije.model.dto.ShowDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

public class ShowControllerTest extends ProjekcijeApplicationTests {

    private static final String URL_PREFIX = "/show";

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
    public void testGetByInstitutionCurrent() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/getByInstitution/" + ShowConstants.DB_ID_INSTITUTION))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(ShowConstants.DB_COUNT_CURRENT_BY_INSTITUTION)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(ShowConstants.DB_ID.intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(ShowConstants.DB_NAME)))
                .andExpect(jsonPath("$.[*].genre").value(hasItem(ShowConstants.DB_GENRE)))
                .andExpect(jsonPath("$.[*].producer").value(hasItem(ShowConstants.DB_PRODUCER)))
                .andExpect(jsonPath("$.[*].duration").value(hasItem(ShowConstants.DB_DURATION)));

    }

    @Test
    public void testGetByInstitutionAll() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/getAllByInstitution/" + ShowConstants.DB_ID_INSTITUTION))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(ShowConstants.DB_COUNT_ALL_BY_INSTITUTION)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(ShowConstants.DB_ID.intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(ShowConstants.DB_NAME)))
                .andExpect(jsonPath("$.[*].genre").value(hasItem(ShowConstants.DB_GENRE)))
                .andExpect(jsonPath("$.[*].producer").value(hasItem(ShowConstants.DB_PRODUCER)))
                .andExpect(jsonPath("$.[*].duration").value(hasItem(ShowConstants.DB_DURATION)));

    }

    @Test
    public void testGetByShowId() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/getById/" + ShowConstants.DB_ID))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(authorities = "ADMIN_INST")
    @Transactional
    @Rollback(true)
    public void testAddShow() throws Exception {
        ShowDTO showDTO = new ShowDTO();
        showDTO.setName(ShowConstants.DB_NAME);
        showDTO.setGenre(ShowConstants.NEW_GENRE);
        showDTO.setProducer(ShowConstants.NEW_PRODUCER);
        showDTO.setDuration(ShowConstants.NEW_DURATION);
        showDTO.setDescription(ShowConstants.NEW_DESCRIPTION);
        showDTO.setIdInstitution(ShowConstants.NEW_ID_INSTITUTION);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(showDTO);

        mockMvc.perform(post(URL_PREFIX + "/addShow").contentType(contentType).content(json)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN_INST")
    @Transactional
    @Rollback(true)
    public void testEditShow() throws Exception {
        ShowDTO showDTO = new ShowDTO();
        showDTO.setId(ShowConstants.DB_ID);
        showDTO.setName(ShowConstants.NEW_NAME);
        showDTO.setGenre(ShowConstants.NEW_GENRE);
        showDTO.setProducer(ShowConstants.NEW_PRODUCER);
        showDTO.setDuration(ShowConstants.NEW_DURATION);
        showDTO.setDescription(ShowConstants.NEW_DESCRIPTION);
        showDTO.setIdInstitution(ShowConstants.NEW_ID_INSTITUTION);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(showDTO);

        mockMvc.perform(put(URL_PREFIX + "/editShow/" + ShowConstants.DB_ID).contentType(contentType).content(json)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN_INST")
    @Transactional
    @Rollback(true)
    public void testDeleteSegmentOK() throws Exception {

        mockMvc.perform(delete(URL_PREFIX + "/deleteShow/" + ShowConstants.DB_ID_TO_DELETE)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN_INST")
    @Transactional
    @Rollback(true)
    public void testDeleteSegmentNotFound() throws Exception {

        mockMvc.perform(delete(URL_PREFIX + "/deleteSegment/" + ShowConstants.DB_ID_NON_EXISTING)).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "ADMIN_INST")
    @Transactional
    @Rollback(true)
    public void testAddActorToShow() throws Exception {
        ShowActorDTO showActorDTO = new ShowActorDTO();
        showActorDTO.setIdActor(ShowConstants.DB_ID_ACTOR);
        showActorDTO.setIdShow(ShowConstants.DB_ID);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(showActorDTO);

        mockMvc.perform(post(URL_PREFIX + "/addActor").contentType(contentType).content(json)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN_INST")
    @Transactional
    @Rollback(true)
    public void testAddActorNonExistingToShow() throws Exception {
        ShowActorDTO showActorDTO = new ShowActorDTO();
        showActorDTO.setIdActor(ShowConstants.DB_ID_ACTOR_NON_EXISTING);
        showActorDTO.setIdShow(ShowConstants.DB_ID);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(showActorDTO);

        mockMvc.perform(post(URL_PREFIX + "/addActor").contentType(contentType).content(json)).andExpect(status().isNotFound());
    }

}
