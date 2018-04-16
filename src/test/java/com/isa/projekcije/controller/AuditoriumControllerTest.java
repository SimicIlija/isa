package com.isa.projekcije.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.projekcije.ProjekcijeApplicationTests;
import com.isa.projekcije.constants.AuditoriumConstants;
import com.isa.projekcije.model.dto.AuditoriumDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

public class AuditoriumControllerTest extends ProjekcijeApplicationTests {

    private static final String URL_PREFIX = "/auditorium";

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
    public void testGetAllAuditoriumsFromInstitution() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/getByInstitution/" + AuditoriumConstants.DB_INSTITUTION_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(AuditoriumConstants.DB_COUNT)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(AuditoriumConstants.DB_ID.intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(AuditoriumConstants.DB_NAME)));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testAddAuditorium() throws Exception {
        AuditoriumDTO auditoriumDTO = new AuditoriumDTO();
        auditoriumDTO.setName(AuditoriumConstants.NEW_NAME);
        auditoriumDTO.setIdInstitution(AuditoriumConstants.DB_INSTITUTION_ID);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(auditoriumDTO);

        mockMvc.perform(post(URL_PREFIX + "/addAuditorium").contentType(contentType).content(json)).andExpect(status().isCreated());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testEditAuditorium() throws Exception {
        AuditoriumDTO auditoriumDTO = new AuditoriumDTO();
        auditoriumDTO.setId(AuditoriumConstants.DB_ID);
        auditoriumDTO.setName(AuditoriumConstants.NEW_NAME);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(auditoriumDTO);

        mockMvc.perform(put(URL_PREFIX + "/editAuditorium/" + AuditoriumConstants.DB_ID).contentType(contentType).content(json)).andExpect(status().isOk());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteInstitutionOK() throws Exception {
        mockMvc.perform(delete(URL_PREFIX + "/deleteAuditorium/" + AuditoriumConstants.DB_ID_TO_DELETE)).andExpect(status().isOk());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteInstitutionNotFound() throws Exception {
        mockMvc.perform(delete(URL_PREFIX + "/deleteAuditorium/" + AuditoriumConstants.DB_NON_EXISTING_ID)).andExpect(status().isNotFound());
    }
}
