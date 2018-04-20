package com.isa.projekcije.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.projekcije.ProjekcijeApplicationTests;
import com.isa.projekcije.constants.RatingConstants;
import com.isa.projekcije.model.dto.LoginDTO;
import com.isa.projekcije.model.dto.ProjectionRatingDTO;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class ProjectionRatingControllerTest extends ProjekcijeApplicationTests {

    private static final String URL_PREFIX = "/projectionRating";

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
    public void testGetByProjection() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/getByProjection/" + RatingConstants.DB_ID_PROJECTION))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(RatingConstants.DB_COUNT)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(RatingConstants.DB_ID.intValue())))
                .andExpect(jsonPath("$.[*].idUser").value(hasItem(RatingConstants.DB_ID_USER.intValue())))
                .andExpect(jsonPath("$.[*].idProjection").value(hasItem(RatingConstants.DB_ID_PROJECTION.intValue())))
                .andExpect(jsonPath("$.[*].projectionRating").value(hasItem(RatingConstants.DB_PROJECTION_RATING)))
                .andExpect(jsonPath("$.[*].institutionRating").value(hasItem(RatingConstants.DB_INSTITUTION_RATING)));
    }

    @Test
    @WithMockUser(authorities = "REGISTERED")
    @Transactional
    @Rollback(true)
    public void testAddProjectionRating() throws Exception {
        final LoginDTO loginDto = new LoginDTO("smiljana@gmail.com", "smiljana");

        ObjectMapper objectMapper1 = new ObjectMapper();
        objectMapper1.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json1 = objectMapper1.writeValueAsString(loginDto);

        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON).content(json1))
                .andExpect(status().isOk());

        ProjectionRatingDTO projectionRatingDTO = new ProjectionRatingDTO();
        projectionRatingDTO.setIdProjection(RatingConstants.NEW_ID_PROJECTION);
        projectionRatingDTO.setIdUser(RatingConstants.NEW_ID_USER);
        projectionRatingDTO.setInstitutionRating(RatingConstants.NEW_INSTITUTION_RATING);
        projectionRatingDTO.setProjectionRating(RatingConstants.NEW_PROJECTION_RATING);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(projectionRatingDTO);

        mockMvc.perform(post(URL_PREFIX + "/addProjectionRating").contentType(contentType).content(json)).andExpect(status().isOk());
    }
}
