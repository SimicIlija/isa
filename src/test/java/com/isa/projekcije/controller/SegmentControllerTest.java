package com.isa.projekcije.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.projekcije.ProjekcijeApplicationTests;
import com.isa.projekcije.constants.SegmentConstants;
import com.isa.projekcije.model.dto.SegmentDTO;
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
public class SegmentControllerTest extends ProjekcijeApplicationTests {

    private static final String URL_PREFIX = "/segment";

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
    public void testGetByAuditorium() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/getByAuditorium/" + SegmentConstants.DB_AUDITORIUM_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(SegmentConstants.DB_BY_AUDITORIUM_COUNT)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(SegmentConstants.DB_ID.intValue())))
                .andExpect(jsonPath("$.[*].label").value(hasItem(SegmentConstants.DB_LABEL)))
                .andExpect(jsonPath("$.[*].rowCount").value(hasItem(SegmentConstants.DB_ROW_COUNT)))
                .andExpect(jsonPath("$.[*].seatsInRowCount").value(hasItem(SegmentConstants.DB_SEATS_IN_ROW_COUNT)));
    }

    @Test
    public void testGetByProjection() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/getByProjection/" + SegmentConstants.DB_PROJECTION_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(SegmentConstants.DB_BY_PROJECTION_COUNT)))
                .andExpect(jsonPath("$.[*].idSegment").value(hasItem(SegmentConstants.DB_ID.intValue())));
    }

    @Test
    @WithMockUser(authorities = "ADMIN_INST")
    @Transactional
    @Rollback(true)
    public void testAddSegment() throws Exception {

        SegmentDTO segmentDTO = new SegmentDTO();
        segmentDTO.setLabel(SegmentConstants.NEW_LABEL);
        segmentDTO.setRowCount(SegmentConstants.NEW_ROW_COUNT);
        segmentDTO.setSeatsInRowCount(SegmentConstants.NEW_SEATS_IN_ROW_COUNT);
        segmentDTO.setIdAuditorium(SegmentConstants.DB_AUDITORIUM_ID);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(segmentDTO);

        mockMvc.perform(post(URL_PREFIX + "/addSegment").contentType(contentType).content(json)).andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(authorities = "ADMIN_INST")
    @Transactional
    @Rollback(true)
    public void testEditSegment() throws Exception {

        SegmentDTO segmentDTO = new SegmentDTO();
        segmentDTO.setId(SegmentConstants.DB_ID);
        segmentDTO.setLabel(SegmentConstants.NEW_LABEL);
        segmentDTO.setRowCount(SegmentConstants.NEW_ROW_COUNT);
        segmentDTO.setSeatsInRowCount(SegmentConstants.NEW_SEATS_IN_ROW_COUNT);
        segmentDTO.setIdAuditorium(SegmentConstants.DB_AUDITORIUM_ID);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(segmentDTO);

        mockMvc.perform(put(URL_PREFIX + "/editSegment/" + SegmentConstants.DB_ID).contentType(contentType).content(json)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN_INST")
    @Transactional
    @Rollback(true)
    public void testDeleteSegmentOK() throws Exception {

        mockMvc.perform(delete(URL_PREFIX + "/deleteSegment/" + SegmentConstants.DB_ID_TO_DELETE)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN_INST")
    @Transactional
    @Rollback(true)
    public void testDeleteSegmentNotFound() throws Exception {

        mockMvc.perform(delete(URL_PREFIX + "/deleteSegment/" + SegmentConstants.DB_NON_EXISTING_ID)).andExpect(status().isNotFound());
    }

}
