package com.isa.projekcije.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.projekcije.ProjekcijeApplicationTests;
import com.isa.projekcije.constants.ProjectionConstants;
import com.isa.projekcije.model.dto.ProjectionDTO;
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
public class ProjectionControllerTest extends ProjekcijeApplicationTests {

    private static final String URL_PREFIX = "/projections";

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
        mockMvc.perform(get(URL_PREFIX + "/getByShow/" + ProjectionConstants.DB_ID_SHOW))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(ProjectionConstants.DB_COUNT_BY_SHOW_ALL_COUNT)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(ProjectionConstants.DB_ID.intValue())))
                .andExpect(jsonPath("$.[*].id_show").value(hasItem(ProjectionConstants.DB_ID_SHOW.intValue())))
                .andExpect(jsonPath("$.[*].id_auditorium").value(hasItem(ProjectionConstants.DB_ID_AUDITORIUM.intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(ProjectionConstants.DB_DATE)));
    }

    @Test
    public void testGetByShowCurrent() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/getByShowCurrent/" + ProjectionConstants.DB_ID_SHOW))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(ProjectionConstants.DB_COUNT_BY_SHOW_CURRENT_COUNT)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(ProjectionConstants.DB_ID.intValue())))
                .andExpect(jsonPath("$.[*].id_show").value(hasItem(ProjectionConstants.DB_ID_SHOW.intValue())))
                .andExpect(jsonPath("$.[*].id_auditorium").value(hasItem(ProjectionConstants.DB_ID_AUDITORIUM.intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(ProjectionConstants.DB_DATE)));
    }

    @Test
    public void testGetByAuditorium() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/getByAuditorium/" + ProjectionConstants.DB_ID_AUDITORIUM))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(ProjectionConstants.DB_COUNT_BY_AUDTORIUM_COUNT)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(ProjectionConstants.DB_ID.intValue())))
                .andExpect(jsonPath("$.[*].id_show").value(hasItem(ProjectionConstants.DB_ID_SHOW.intValue())))
                .andExpect(jsonPath("$.[*].id_auditorium").value(hasItem(ProjectionConstants.DB_ID_AUDITORIUM.intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(ProjectionConstants.DB_DATE)));
    }

    @Test
    @WithMockUser(authorities = "ADMIN_INST")
    @Transactional
    @Rollback(true)
    public void testAddProjection() throws Exception {
        ProjectionDTO projectionDTO = new ProjectionDTO();
        projectionDTO.setId_auditorium(ProjectionConstants.NEW_ID_AUDITORIUM);
        projectionDTO.setId_show(ProjectionConstants.NEW_ID_SHOW);
        projectionDTO.setDate(ProjectionConstants.NEW_DATE);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(projectionDTO);

        mockMvc.perform(post(URL_PREFIX + "/addProjection").contentType(contentType).content(json)).andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(authorities = "ADMIN_INST")
    @Transactional
    @Rollback(true)
    public void testAddProjectionInvalidDateFormat() throws Exception {
        ProjectionDTO projectionDTO = new ProjectionDTO();
        projectionDTO.setId_auditorium(ProjectionConstants.NEW_ID_AUDITORIUM);
        projectionDTO.setId_show(ProjectionConstants.NEW_ID_SHOW);
        projectionDTO.setDate(ProjectionConstants.NEW_DATE_INVALID_FORMAT);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(projectionDTO);

        mockMvc.perform(post(URL_PREFIX + "/addProjection").contentType(contentType).content(json)).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "ADMIN_INST")
    @Transactional
    @Rollback(true)
    public void testDeleteProjctionOK() throws Exception {

        mockMvc.perform(delete(URL_PREFIX + "/deleteProjection/" + ProjectionConstants.DB_ID_TO_DELETE)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN_INST")
    @Transactional
    @Rollback(true)
    public void testDeleteProjectionBadRequest() throws Exception {

        mockMvc.perform(delete(URL_PREFIX + "/deleteProjection/" + ProjectionConstants.DB_ID_TO_DELETE_BAD_REQUEST)).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "ADMIN_INST")
    @Transactional
    @Rollback(true)
    public void testEditProjection() throws Exception {
        ProjectionDTO projectionDTO = new ProjectionDTO();
        projectionDTO.setId(ProjectionConstants.DB_ID);
        projectionDTO.setId_auditorium(ProjectionConstants.NEW_ID_AUDITORIUM);
        projectionDTO.setId_show(ProjectionConstants.NEW_ID_SHOW);
        projectionDTO.setDate(ProjectionConstants.NEW_DATE);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(projectionDTO);

        mockMvc.perform(put(URL_PREFIX + "/editProjection/" + ProjectionConstants.DB_ID).contentType(contentType).content(json)).andExpect(status().isOk());
    }

    @Test
    public void testGetByDateAndShow() throws Exception {
        ProjectionDTO projectionDTO = new ProjectionDTO();
        projectionDTO.setId_show(ProjectionConstants.DB_ID_SHOW);
        projectionDTO.setDate(ProjectionConstants.DB_DATE_GET_BY_DATE);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(projectionDTO);
        mockMvc.perform(post(URL_PREFIX + "/getProjectionByDate").contentType(contentType).content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(ProjectionConstants.DB_COUNT_BY_SHOW_ALL_COUNT)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(ProjectionConstants.DB_ID.intValue())))
                .andExpect(jsonPath("$.[*].id_show").value(hasItem(ProjectionConstants.DB_ID_SHOW.intValue())))
                .andExpect(jsonPath("$.[*].id_auditorium").value(hasItem(ProjectionConstants.DB_ID_AUDITORIUM.intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(ProjectionConstants.DB_DATE)));
    }

    @Test
    public void testGetById() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/getProjection/" + ProjectionConstants.DB_ID))
                .andExpect(status().isOk());
    }
}
