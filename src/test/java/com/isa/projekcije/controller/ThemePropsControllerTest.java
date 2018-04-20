package com.isa.projekcije.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.projekcije.ProjekcijeApplicationTests;
import com.isa.projekcije.model.dto.ThemePropsDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class ThemePropsControllerTest extends ProjekcijeApplicationTests {
    private final String apiUrl = "/api/themeprops";

    @Autowired
    WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void unsuccessfulDelete() throws Exception {
        mockMvc.perform(delete(apiUrl + "/0")).andExpect(status().isNotFound());
    }

    @Test
    @Rollback
    public void successfulDelete() throws Exception {
        mockMvc.perform(delete(apiUrl + "/1")).andExpect(status().isOk());
    }

    @Test
    @Rollback
    public void successfulCreate() throws Exception {
        ThemePropsDTO dto = new ThemePropsDTO();
        dto.setAmount(12);
        dto.setDescription("des");
        dto.setName("nam");
        dto.setPrice(123);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post(apiUrl + "/add").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @Rollback
    public void successfulUpdate() throws Exception {
        ThemePropsDTO dto = new ThemePropsDTO();
        dto.setAmount(12);
        dto.setDescription("des");
        dto.setName("naadsasdm");
        dto.setPrice(123);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(put(apiUrl + "/2").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void invalidDtoUpdate() throws Exception {
        ThemePropsDTO dto = new ThemePropsDTO();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(put(apiUrl + "/124421").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void unsuccessfulUpdate() throws Exception {
        ThemePropsDTO dto = new ThemePropsDTO();
        dto.setAmount(12);
        dto.setDescription("des");
        dto.setName("naadsasdm");
        dto.setPrice(123);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(put(apiUrl + "/123132132").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isNotFound());
    }
}
