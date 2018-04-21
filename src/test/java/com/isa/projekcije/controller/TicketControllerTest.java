package com.isa.projekcije.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.projekcije.ProjekcijeApplicationTests;
import com.isa.projekcije.model.dto.SegmentTicketsDTO;
import com.isa.projekcije.model.dto.TicketDTO;
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

import java.math.BigDecimal;
import java.nio.charset.Charset;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class TicketControllerTest extends ProjekcijeApplicationTests {
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


    private static final String BASE_URL = "/tickets";
    private static final String ADD_TICKETS_FOR_SEGMENT = "/addTicketsForSegment";
    private static final String ADD_TICKET = "/addTicket";
    private static final String DELETE_TICKET_FOR_SEGMENT = "/deleteTicketsForSegment";


    @Test
    @Transactional
    @Rollback(true)
    public void addTicketForNonExistingSegmentShouldReturnBadRequest() {
        SegmentTicketsDTO segmentTicketsDTO = new SegmentTicketsDTO();
        segmentTicketsDTO.setIdProjection(1L);
        segmentTicketsDTO.setPrice(300.00);
        segmentTicketsDTO.setIdSegment(10L);


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = null;
        try {
            json = objectMapper.writeValueAsString(segmentTicketsDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            mockMvc.perform(post(BASE_URL + ADD_TICKETS_FOR_SEGMENT).contentType(contentType).content(json))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    @Rollback(true)
    public void addTicketForNonExistingProjectionShouldReturnBadRequest() {
        SegmentTicketsDTO segmentTicketsDTO = new SegmentTicketsDTO();
        segmentTicketsDTO.setIdProjection(10L);
        segmentTicketsDTO.setPrice(300.00);
        segmentTicketsDTO.setIdSegment(1L);


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = null;
        try {
            json = objectMapper.writeValueAsString(segmentTicketsDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            mockMvc.perform(post(BASE_URL + ADD_TICKETS_FOR_SEGMENT).contentType(contentType).content(json))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    @Rollback(true)
    public void addTicketForSegmentReturnsOk() {
        SegmentTicketsDTO segmentTicketsDTO = new SegmentTicketsDTO();
        segmentTicketsDTO.setIdProjection(1L);
        segmentTicketsDTO.setPrice(300.00);
        segmentTicketsDTO.setIdSegment(1L);


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = null;
        try {
            json = objectMapper.writeValueAsString(segmentTicketsDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            mockMvc.perform(post(BASE_URL + ADD_TICKETS_FOR_SEGMENT).contentType(contentType).content(json))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.[*].idProjection").value(hasItem(1L)))
                    .andExpect(jsonPath("$.[*].idSegment").value(hasItem(1L)))
                    .andExpect(jsonPath("$.[*].price").value(hasItem(300.00)))
            ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    @Rollback(true)
    public void addTicketWithoutSeatReturnsBasRequest() {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setId_projection(1L);
        BigDecimal price = new BigDecimal("400.00");
        ticketDTO.setPrice(price);
        ticketDTO.setReserved(true);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = null;
        try {
            json = objectMapper.writeValueAsString(ticketDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            mockMvc.perform(post(BASE_URL + ADD_TICKET).contentType(contentType).content(json))
                    .andExpect(status().isBadRequest());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    @Transactional
    @Rollback(true)
    public void addTicketWithoutProjectionReturnsBasRequest() {
        TicketDTO ticketDTO = new TicketDTO();

        ticketDTO.setId_seat(1L);
        BigDecimal price = new BigDecimal("400.00");
        ticketDTO.setPrice(price);
        ticketDTO.setReserved(true);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = null;
        try {
            json = objectMapper.writeValueAsString(ticketDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            mockMvc.perform(post(BASE_URL + ADD_TICKET).contentType(contentType).content(json))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.[*].price").value(hasItem(price)))
                    .andExpect(jsonPath("$.[*].id_seat").value(hasItem(1L)))
                    .andExpect(jsonPath("$.[*].id_projection").value(hasItem(1L)))
                    .andExpect(jsonPath("$.[*].reserved").value(hasItem(true)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    @Transactional
    @Rollback(true)
    public void addTicketReturnsOk() {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setId_projection(1L);
        ticketDTO.setId_seat(1L);
        BigDecimal price = new BigDecimal("400.00");
        ticketDTO.setPrice(price);
        ticketDTO.setReserved(true);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = null;
        try {
            json = objectMapper.writeValueAsString(ticketDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            mockMvc.perform(post(BASE_URL + ADD_TICKET).contentType(contentType).content(json))
                    .andExpect(status().isBadRequest());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    @Rollback(true)
    public void deleteTicketsForSegmentsReturnsOk() {
        SegmentTicketsDTO segmentTicketsDTO = new SegmentTicketsDTO();
        segmentTicketsDTO.setIdProjection(1L);
        segmentTicketsDTO.setPrice(300.00);
        segmentTicketsDTO.setIdSegment(1L);


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = null;
        try {
            json = objectMapper.writeValueAsString(segmentTicketsDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            mockMvc.perform(delete(BASE_URL + DELETE_TICKET_FOR_SEGMENT).contentType(contentType).content(json))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.[*].idProjection").value(hasItem(1L)))
                    .andExpect(jsonPath("$.[*].idSegment").value(hasItem(1L)))
                    .andExpect(jsonPath("$.[*].price").value(hasItem(300.00)))
            ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    @Transactional
    @Rollback(true)
    public void deleteTicketsWithoutSegmentsReturnsNotFound() {
        SegmentTicketsDTO segmentTicketsDTO = new SegmentTicketsDTO();
        segmentTicketsDTO.setIdProjection(1L);
        segmentTicketsDTO.setPrice(300.00);


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = null;
        try {
            json = objectMapper.writeValueAsString(segmentTicketsDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            mockMvc.perform(delete(BASE_URL + DELETE_TICKET_FOR_SEGMENT).contentType(contentType).content(json))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    @Transactional
    @Rollback(true)
    public void deleteTicketsWithoutProjectionReturnsNotFound() {
        SegmentTicketsDTO segmentTicketsDTO = new SegmentTicketsDTO();
        segmentTicketsDTO.setIdSegment(1L);
        segmentTicketsDTO.setPrice(300.00);


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = null;
        try {
            json = objectMapper.writeValueAsString(segmentTicketsDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            mockMvc.perform(delete(BASE_URL + DELETE_TICKET_FOR_SEGMENT).contentType(contentType).content(json))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    @Transactional
    @Rollback(true)
    public void deleteTicketsForProjectionInWrongSegmentReturnsNotFound() {
        SegmentTicketsDTO segmentTicketsDTO = new SegmentTicketsDTO();
        segmentTicketsDTO.setIdSegment(2L);
        segmentTicketsDTO.setPrice(300.00);
        segmentTicketsDTO.setIdProjection(1L);


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = null;
        try {
            json = objectMapper.writeValueAsString(segmentTicketsDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            mockMvc.perform(delete(BASE_URL + DELETE_TICKET_FOR_SEGMENT).contentType(contentType).content(json))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    @Transactional
    @Rollback(true)
    public void deleteTicketsReturnsOk() {
        SegmentTicketsDTO segmentTicketsDTO = new SegmentTicketsDTO();
        segmentTicketsDTO.setIdSegment(1L);
        segmentTicketsDTO.setPrice(300.00);
        segmentTicketsDTO.setIdProjection(1L);


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = null;
        try {
            json = objectMapper.writeValueAsString(segmentTicketsDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            mockMvc.perform(delete(BASE_URL + DELETE_TICKET_FOR_SEGMENT).contentType(contentType).content(json))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.[*].idProjection").value(hasItem(1L)))
                    .andExpect(jsonPath("$.[*].idSegment").value(hasItem(1L)))
                    .andExpect(jsonPath("$.[*].price").value(hasItem(300.00)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
