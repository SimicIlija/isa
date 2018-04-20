package com.isa.projekcije.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.projekcije.ProjekcijeApplicationTests;
import com.isa.projekcije.constants.SeatConstants;
import com.isa.projekcije.model.dto.SeatDTO;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class SeatControllerTest extends ProjekcijeApplicationTests {

    private static final String URL_PREFIX = "/seats";

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
    @WithMockUser(authorities = "ADMIN_INST")
    @Transactional
    @Rollback(true)
    public void testAddSeat() throws Exception {
        SeatDTO seatDTO = new SeatDTO();
        seatDTO.setRow(SeatConstants.NEW_ROW);
        seatDTO.setSeatNumber(SeatConstants.NEW_SEAT_NUMBER);
        seatDTO.setSegmentId(SeatConstants.DB_SEGMENT_ID);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(seatDTO);

        mockMvc.perform(post(URL_PREFIX + "/addSeat").contentType(contentType).content(json)).andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(authorities = "ADMIN_INST")
    @Transactional
    @Rollback(true)
    public void testEditSeat() throws Exception {

        SeatDTO seatDTO = new SeatDTO();
        seatDTO.setId(SeatConstants.DB_ID);
        seatDTO.setRow(SeatConstants.NEW_ROW);
        seatDTO.setSeatNumber(SeatConstants.NEW_SEAT_NUMBER);
        seatDTO.setSegmentId(SeatConstants.DB_SEGMENT_ID);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(seatDTO);

        mockMvc.perform(put(URL_PREFIX + "/" + SeatConstants.DB_ID).contentType(contentType).content(json)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN_INST")
    @Transactional
    @Rollback(true)
    public void testDeleteSeatOK() throws Exception {

        mockMvc.perform(delete(URL_PREFIX + "/" + SeatConstants.DB_ID_TO_DELETE)).andExpect(status().isOk());
    }

}
