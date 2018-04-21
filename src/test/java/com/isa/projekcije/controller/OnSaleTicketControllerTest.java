package com.isa.projekcije.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.projekcije.ProjekcijeApplicationTests;
import com.isa.projekcije.constants.OnSaleTicketConstants;
import com.isa.projekcije.model.dto.LoginDTO;
import com.isa.projekcije.model.dto.OnSaleTicketDTO;
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

public class OnSaleTicketControllerTest extends ProjekcijeApplicationTests {

    private static final String URL_PREFIX = "/onSaleTicket";

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
    public void testAddOnSateTicket() throws Exception {
        OnSaleTicketDTO onSaleTicketDTO = new OnSaleTicketDTO();
        onSaleTicketDTO.setIdSegment(OnSaleTicketConstants.NEW_ID_SEGMENT);
        onSaleTicketDTO.setSeatRow(OnSaleTicketConstants.NEW_SEAT_ROW);
        onSaleTicketDTO.setSeatNumber(OnSaleTicketConstants.NEW_SEAT_NUMBER);
        onSaleTicketDTO.setIdProjection(OnSaleTicketConstants.NEW_ID_PROJECTION);
        onSaleTicketDTO.setDiscount(OnSaleTicketConstants.NEW_DISCOUNT);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(onSaleTicketDTO);

        mockMvc.perform(post(URL_PREFIX + "/addOnSaleTicket").contentType(contentType).content(json)).andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(authorities = "ADMIN_INST")
    @Transactional
    @Rollback(true)
    public void testAddOnSateTicketInvalidSeatRow() throws Exception {
        OnSaleTicketDTO onSaleTicketDTO = new OnSaleTicketDTO();
        onSaleTicketDTO.setIdSegment(OnSaleTicketConstants.NEW_ID_SEGMENT);
        onSaleTicketDTO.setSeatRow(OnSaleTicketConstants.NEW_SEAT_ROW_INVALID);
        onSaleTicketDTO.setSeatNumber(OnSaleTicketConstants.NEW_SEAT_NUMBER);
        onSaleTicketDTO.setIdProjection(OnSaleTicketConstants.NEW_ID_PROJECTION);
        onSaleTicketDTO.setDiscount(OnSaleTicketConstants.NEW_DISCOUNT);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(onSaleTicketDTO);

        mockMvc.perform(post(URL_PREFIX + "/addOnSaleTicket").contentType(contentType).content(json)).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "ADMIN_INST")
    @Transactional
    @Rollback(true)
    public void testAddOnSateTicketSegmentNotAvailable() throws Exception {
        OnSaleTicketDTO onSaleTicketDTO = new OnSaleTicketDTO();
        onSaleTicketDTO.setIdSegment(OnSaleTicketConstants.NEW_ID_SEGMENT_NOT_AVAILABLE);
        onSaleTicketDTO.setSeatRow(OnSaleTicketConstants.NEW_SEAT_ROW);
        onSaleTicketDTO.setSeatNumber(OnSaleTicketConstants.NEW_SEAT_NUMBER);
        onSaleTicketDTO.setIdProjection(OnSaleTicketConstants.NEW_ID_PROJECTION);
        onSaleTicketDTO.setDiscount(OnSaleTicketConstants.NEW_DISCOUNT);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(onSaleTicketDTO);

        mockMvc.perform(post(URL_PREFIX + "/addOnSaleTicket").contentType(contentType).content(json)).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "ADMIN_INST")
    @Transactional
    @Rollback(true)
    public void testAddOnSateTicketProjectionNotAvailable() throws Exception {
        OnSaleTicketDTO onSaleTicketDTO = new OnSaleTicketDTO();
        onSaleTicketDTO.setIdSegment(OnSaleTicketConstants.NEW_ID_SEGMENT);
        onSaleTicketDTO.setSeatRow(OnSaleTicketConstants.NEW_SEAT_ROW);
        onSaleTicketDTO.setSeatNumber(OnSaleTicketConstants.NEW_SEAT_NUMBER);
        onSaleTicketDTO.setIdProjection(OnSaleTicketConstants.NEW_ID_PROJECTION_NON_INVALID);
        onSaleTicketDTO.setDiscount(OnSaleTicketConstants.NEW_DISCOUNT);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(onSaleTicketDTO);

        mockMvc.perform(post(URL_PREFIX + "/addOnSaleTicket").contentType(contentType).content(json)).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "ADMIN_INST")
    @Transactional
    @Rollback(true)
    public void testAddOnSateTicketInvalidDiscount() throws Exception {
        OnSaleTicketDTO onSaleTicketDTO = new OnSaleTicketDTO();
        onSaleTicketDTO.setIdSegment(OnSaleTicketConstants.NEW_ID_SEGMENT);
        onSaleTicketDTO.setSeatRow(OnSaleTicketConstants.NEW_SEAT_ROW);
        onSaleTicketDTO.setSeatNumber(OnSaleTicketConstants.NEW_SEAT_NUMBER);
        onSaleTicketDTO.setIdProjection(OnSaleTicketConstants.NEW_ID_PROJECTION);
        onSaleTicketDTO.setDiscount(OnSaleTicketConstants.NEW_DISCOUNT_INVALID);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(onSaleTicketDTO);

        mockMvc.perform(post(URL_PREFIX + "/addOnSaleTicket").contentType(contentType).content(json)).andExpect(status().isBadRequest());
    }

    @Test
    public void testGetOnSaleTicketsByProjectionAndSegment() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/getOnSaleTickets/" + OnSaleTicketConstants.DB_ID_PROJECTION + "/" + OnSaleTicketConstants.DB_ID_SEGMENT))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(OnSaleTicketConstants.DB_COUNT)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(OnSaleTicketConstants.DB_ID.intValue())))
                .andExpect(jsonPath("$.[*].idProjection").value(hasItem(OnSaleTicketConstants.DB_ID_PROJECTION.intValue())))
                .andExpect(jsonPath("$.[*].idTicket").value(hasItem(OnSaleTicketConstants.DB_ID_TICKET.intValue())))
                .andExpect(jsonPath("$.[*].oldPrice").value(hasItem(OnSaleTicketConstants.DB_OLD_PRICE)))
                .andExpect(jsonPath("$.[*].discount").value(hasItem(OnSaleTicketConstants.DB_DISCOUNT)))
                .andExpect(jsonPath("$.[*].showName").value(hasItem(OnSaleTicketConstants.DB_SHOW_NAME)))
                .andExpect(jsonPath("$.[*].showDate").value(hasItem(OnSaleTicketConstants.DB_SHOW_DATE)))
                .andExpect(jsonPath("$.[*].auditoriumName").value(hasItem(OnSaleTicketConstants.DB_AUDITORIUM_NAME)))
                .andExpect(jsonPath("$.[*].idSegment").value(hasItem(OnSaleTicketConstants.DB_ID_SEGMENT.intValue())))
                .andExpect(jsonPath("$.[*].segmentName").value(hasItem(OnSaleTicketConstants.DB_SEGMENT_NAME)))
                .andExpect(jsonPath("$.[*].seatRow").value(hasItem(OnSaleTicketConstants.DB_SEAT_ROW)))
                .andExpect(jsonPath("$.[*].seatNumber").value(hasItem(OnSaleTicketConstants.DB_SEAT_NUMBER)));
    }

    @Test
    @WithMockUser(authorities = "ADMIN_INST")
    @Transactional
    @Rollback(true)
    public void testEditOnSateTicket() throws Exception {
        OnSaleTicketDTO onSaleTicketDTO = new OnSaleTicketDTO();
        onSaleTicketDTO.setDiscount(OnSaleTicketConstants.NEW_DISCOUNT);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(onSaleTicketDTO);

        mockMvc.perform(put(URL_PREFIX + "/editOnSaleTicket/" + OnSaleTicketConstants.DB_ID).contentType(contentType).content(json)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN_INST")
    @Transactional
    @Rollback(true)
    public void testEditOnSateTicketNonExisting() throws Exception {
        OnSaleTicketDTO onSaleTicketDTO = new OnSaleTicketDTO();
        onSaleTicketDTO.setDiscount(OnSaleTicketConstants.NEW_DISCOUNT);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(onSaleTicketDTO);

        mockMvc.perform(put(URL_PREFIX + "/editOnSaleTicket/" + OnSaleTicketConstants.DB_NON_EXISTING_ID).contentType(contentType).content(json)).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "ADMIN_INST")
    @Transactional
    @Rollback(true)
    public void testDeleteOnSateTicketNonExisting() throws Exception {
        mockMvc.perform(delete(URL_PREFIX + "/deleteOnSaleTicket/" + OnSaleTicketConstants.DB_NON_EXISTING_ID)).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "ADMIN_INST")
    @Transactional
    @Rollback(true)
    public void testDeleteOnSateTicket() throws Exception {
        mockMvc.perform(delete(URL_PREFIX + "/deleteOnSaleTicket/" + OnSaleTicketConstants.DB_ID)).andExpect(status().isOk());
    }

    @Test
    public void testGetOnSaleTicketsByInstitution() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/getOnSaleTicketsByInstitution/" + OnSaleTicketConstants.DB_ID_INSTITUTION))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(OnSaleTicketConstants.DB_BY_INSTITUTION_COUNT)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(OnSaleTicketConstants.DB_ID.intValue())))
                .andExpect(jsonPath("$.[*].idProjection").value(hasItem(OnSaleTicketConstants.DB_ID_PROJECTION.intValue())))
                .andExpect(jsonPath("$.[*].idTicket").value(hasItem(OnSaleTicketConstants.DB_ID_TICKET.intValue())))
                .andExpect(jsonPath("$.[*].oldPrice").value(hasItem(OnSaleTicketConstants.DB_OLD_PRICE)))
                .andExpect(jsonPath("$.[*].discount").value(hasItem(OnSaleTicketConstants.DB_DISCOUNT)))
                .andExpect(jsonPath("$.[*].showName").value(hasItem(OnSaleTicketConstants.DB_SHOW_NAME)))
                .andExpect(jsonPath("$.[*].showDate").value(hasItem(OnSaleTicketConstants.DB_SHOW_DATE)))
                .andExpect(jsonPath("$.[*].auditoriumName").value(hasItem(OnSaleTicketConstants.DB_AUDITORIUM_NAME)))
                .andExpect(jsonPath("$.[*].idSegment").value(hasItem(OnSaleTicketConstants.DB_ID_SEGMENT.intValue())))
                .andExpect(jsonPath("$.[*].segmentName").value(hasItem(OnSaleTicketConstants.DB_SEGMENT_NAME)))
                .andExpect(jsonPath("$.[*].seatRow").value(hasItem(OnSaleTicketConstants.DB_SEAT_ROW)))
                .andExpect(jsonPath("$.[*].seatNumber").value(hasItem(OnSaleTicketConstants.DB_SEAT_NUMBER)));
    }

    @Test
    @WithMockUser(authorities = "REGISTERED")
    @Transactional
    @Rollback(true)
    public void testReserveOnSateTicket() throws Exception {
        final LoginDTO loginDto = new LoginDTO("smiljana@gmail.com", "smiljana");

        ObjectMapper objectMapper1 = new ObjectMapper();
        objectMapper1.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json1 = objectMapper1.writeValueAsString(loginDto);

        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON).content(json1))
                .andExpect(status().isOk());
        mockMvc.perform(post(URL_PREFIX + "/reserveOnSaleTicket/" + OnSaleTicketConstants.DB_ID)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "REGISTERED")
    @Transactional
    @Rollback(true)
    public void testReserveOnSateTicketNotFound() throws Exception {
        final LoginDTO loginDto = new LoginDTO("smiljana@gmail.com", "smiljana");

        ObjectMapper objectMapper1 = new ObjectMapper();
        objectMapper1.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json1 = objectMapper1.writeValueAsString(loginDto);

        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON).content(json1))
                .andExpect(status().isOk());
        mockMvc.perform(post(URL_PREFIX + "/reserveOnSaleTicket/" + OnSaleTicketConstants.DB_NON_EXISTING_ID)).andExpect(status().isNotFound());
    }
}
