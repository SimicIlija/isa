package com.isa.projekcije.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.projekcije.ProjekcijeApplicationTests;
import com.isa.projekcije.constants.InstitutionConstants;
import com.isa.projekcije.model.dto.InstitutionDTO;
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

public class InstitutionControllerTest extends ProjekcijeApplicationTests {

    private static final String URL_PREFIX = "/institution";

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
    public void testGetAllInstitutions() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/getInstitutions")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(InstitutionConstants.DB_COUNT)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(InstitutionConstants.DB_ID.intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(InstitutionConstants.DB_NAME)))
                .andExpect(jsonPath("$.[*].longitude").value(hasItem(InstitutionConstants.DB_LONGITUDE)))
                .andExpect(jsonPath("$.[*].latitude").value(hasItem(InstitutionConstants.DB_LATITUDE)));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testAddInstitution() throws Exception {
        InstitutionDTO institutionDTO = new InstitutionDTO();
        institutionDTO.setName(InstitutionConstants.NEW_NAME);
        institutionDTO.setDescription(InstitutionConstants.NEW_DESCRIPTION);
        institutionDTO.setLongitude(InstitutionConstants.NEW_LONGITUDE);
        institutionDTO.setLatitude(InstitutionConstants.NEW_LATITUDE);
        institutionDTO.setCinema(InstitutionConstants.NEW_ISCINEMA);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(institutionDTO);

        mockMvc.perform(post(URL_PREFIX).contentType(contentType).content(json)).andExpect(status().isCreated());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testEditInstitution() throws Exception {
        InstitutionDTO institutionDTO = new InstitutionDTO();
        institutionDTO.setId(InstitutionConstants.DB_ID);
        institutionDTO.setName(InstitutionConstants.NEW_NAME);
        institutionDTO.setDescription(InstitutionConstants.NEW_DESCRIPTION);
        institutionDTO.setLongitude(InstitutionConstants.NEW_LONGITUDE);
        institutionDTO.setLatitude(InstitutionConstants.NEW_LATITUDE);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(institutionDTO);

        mockMvc.perform(put(URL_PREFIX + "/editInstitution/" + InstitutionConstants.DB_ID).contentType(contentType).content(json)).andExpect(status().isOk());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteInstitutionOK() throws Exception {
        mockMvc.perform(delete(URL_PREFIX + "/" + InstitutionConstants.DB_ID_TO_DELETE)).andExpect(status().isOk());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteInstitutionNotFound() throws Exception {
        mockMvc.perform(delete(URL_PREFIX + "/" + InstitutionConstants.DB_NOT_EXISTING_ID)).andExpect(status().isNotFound());
    }
}
