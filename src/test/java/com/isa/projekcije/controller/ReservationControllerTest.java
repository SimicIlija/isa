package com.isa.projekcije.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.projekcije.ProjekcijeApplicationTests;
import com.isa.projekcije.constants.ReservationConstrants;
import com.isa.projekcije.model.Friendship;
import com.isa.projekcije.model.User;
import com.isa.projekcije.model.dto.ReservationDTO;
import com.isa.projekcije.service.UserService;
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

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class ReservationControllerTest extends ProjekcijeApplicationTests {

    @Autowired
    UserService userService;

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

    private static final String BASE_URL = "/reservations";
    private static final String CREATE = "/createReservation";
    private static final String RESERVER_RESERVATIONS = "/getReserverReservations";
    private static final String CANCEL_RESERVATION = "/cancelReservation/";
    private static final String GET_INVITES = "/getInvites/";
    private static final String GET_RESERVER_RESERVATIONS = "/getReserverReservations";


    @Test
    @Transactional
    @Rollback(true)
    public void createReservation() throws Exception {
        List<String> friends = new ArrayList<String>();
        List<Long> seats = new ArrayList<Long>();
        friends.add("js.lenchi@gmail.com");
        friends.add("marko@gmail.com");
        seats.add(1L);
        seats.add(2L);
        seats.add(3L);

        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(ReservationConstrants.DB_RESERVATION_ID_CREATED);
        reservationDTO.setFriends(friends);
        reservationDTO.setIdReserver(ReservationConstrants.DB_RESERVATION_ID_CREATOR);
        reservationDTO.setIdProjection(ReservationConstrants.DB_RESERVATION_ID_PROJECTION);
        reservationDTO.setIdSeat(seats);

        User loggedIn = userService.findById(ReservationConstrants.DB_RESERVATION_ID_CREATOR);
        userService.setCurrentUser(loggedIn);

        User jelena = userService.findByEmail(friends.get(0));
        User marko = userService.findByEmail(friends.get(1));
        Friendship friendship1 = new Friendship(loggedIn, jelena);
        Friendship friendship2 = new Friendship(loggedIn, marko);


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(reservationDTO);


        mockMvc.perform(post(BASE_URL + CREATE).contentType(contentType).content(json))
                .andExpect(status().isNotFound());


    }

    @Test
    @Transactional
    @Rollback(true)
    public void createReservationWithInvalidProjectionShouldReturnBadRequest() throws Exception {
        List<String> friends = new ArrayList<String>();
        List<Long> seats = new ArrayList<Long>();

        friends.add("js.lenchi@gmail.com");
        seats.add(1L);
        seats.add(2L);

        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(ReservationConstrants.DB_RESERVATION_ID_CREATED);
        reservationDTO.setFriends(friends);
        reservationDTO.setIdReserver(ReservationConstrants.DB_RESERVATION_ID_CREATOR);
        reservationDTO.setIdProjection(ReservationConstrants.DB_RESERVATION_ID_PROJECTION_INVALID);
        reservationDTO.setIdSeat(seats);

        User loggedIn = userService.findById(ReservationConstrants.DB_RESERVATION_ID_CREATOR);
        userService.setCurrentUser(loggedIn);

        User user = userService.findByEmail(friends.get(0));
        Friendship friendship = new Friendship(loggedIn, user);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(reservationDTO);


        mockMvc.perform(post(BASE_URL + CREATE).contentType(contentType).content(json))
                .andExpect(status().isBadRequest());


    }

    @Test
    @Transactional
    @Rollback(true)
    public void createReservationWithNonFriendsShouldReturnNotFound() throws Exception {
        List<String> friends = new ArrayList<String>();
        List<Long> seats = new ArrayList<Long>();
        friends.add("marko@gmail.com");
        seats.add(1L);
        seats.add(2L);


        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(ReservationConstrants.DB_RESERVATION_ID_CREATED);
        reservationDTO.setFriends(friends);
        reservationDTO.setIdReserver(ReservationConstrants.DB_RESERVATION_ID_CREATOR);
        reservationDTO.setIdProjection(ReservationConstrants.DB_RESERVATION_ID_PROJECTION);
        reservationDTO.setIdSeat(seats);

        User loggedIn = userService.findById(ReservationConstrants.DB_RESERVATION_ID_CREATOR);
        userService.setCurrentUser(loggedIn);


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(reservationDTO);


        mockMvc.perform(post(BASE_URL + CREATE).contentType(contentType).content(json))
                .andExpect(status().isNotFound());


    }

    @Test
    @Transactional
    @Rollback(true)
    public void createReservationNumberOfFriendsNotEqualNumberOfSeatsShouldReturnNotFound() throws Exception {
        List<String> friends = new ArrayList<String>();
        List<Long> seats = new ArrayList<Long>();
        friends.add("js.lenchi@gmail.com");
        seats.add(1L);


        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(ReservationConstrants.DB_RESERVATION_ID_CREATED);
        reservationDTO.setFriends(friends);
        reservationDTO.setIdReserver(ReservationConstrants.DB_RESERVATION_ID_CREATOR);
        reservationDTO.setIdProjection(ReservationConstrants.DB_RESERVATION_ID_PROJECTION);
        reservationDTO.setIdSeat(seats);

        User loggedIn = userService.findById(ReservationConstrants.DB_RESERVATION_ID_CREATOR);
        userService.setCurrentUser(loggedIn);
        User user = userService.findByEmail(friends.get(0));
        Friendship friendship = new Friendship(loggedIn, user);


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(reservationDTO);


        mockMvc.perform(post(BASE_URL + CREATE).contentType(contentType).content(json))
                .andExpect(status().isBadRequest());


    }


    @Test
    @Transactional
    @Rollback(true)
    public void createReservationNoSeatsShouldReturnNotFound() throws Exception {
        List<String> friends = new ArrayList<String>();
        List<Long> seats = new ArrayList<Long>();
        friends.add("js.lenchi@gmail.com");


        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(ReservationConstrants.DB_RESERVATION_ID_CREATED);
        reservationDTO.setFriends(friends);
        reservationDTO.setIdReserver(ReservationConstrants.DB_RESERVATION_ID_CREATOR);
        reservationDTO.setIdProjection(ReservationConstrants.DB_RESERVATION_ID_PROJECTION);
        reservationDTO.setIdSeat(seats);

        User loggedIn = userService.findById(ReservationConstrants.DB_RESERVATION_ID_CREATOR);
        userService.setCurrentUser(loggedIn);
        User user = userService.findByEmail(friends.get(0));
        Friendship friendship = new Friendship(loggedIn, user);


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(reservationDTO);


        mockMvc.perform(post(BASE_URL + CREATE).contentType(contentType).content(json))
                .andExpect(status().isBadRequest());


    }


    @Test
    @Transactional
    @Rollback(true)
    public void cancelReservationShouldReturnOk() {

        try {
            mockMvc.perform(delete(BASE_URL + CANCEL_RESERVATION + ReservationConstrants.DB_RESERVATION_TO_DELETE)).andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    @Rollback(true)
    public void cancelNotExistingReservationShouldReturnNotFound() {
        try {
            mockMvc.perform(delete(BASE_URL + CANCEL_RESERVATION + ReservationConstrants.DB_RESERVATION_TO_DELETE_INVALID)).andExpect(status().isNotFound());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getNonExistingInvitesShouldReturnNotFound() {
        try {
            mockMvc.perform(post(BASE_URL + GET_INVITES + ReservationConstrants.DB_USER_WITH_NO_INVITES)).andExpect(status().isNotFound());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getExistingInvitesShouldReturnOk() {
        try {
            mockMvc.perform(post(BASE_URL + GET_INVITES + ReservationConstrants.DB_USER_WITH_INVITES)).andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getNonExistingReservationsForReserverShouldReturnNotFound() {
        User user = userService.findById(ReservationConstrants.DB_USER_WITH_NO_RESERVATIONS);
        userService.setCurrentUser(user);
        try {
            mockMvc.perform(post(BASE_URL + GET_RESERVER_RESERVATIONS)).andExpect(status().isNotFound());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getReservationsForReserverShouldReturnOk() {
        User user = userService.findById(ReservationConstrants.DB_USER_WITH_RESERVATIONS);
        userService.setCurrentUser(user);
        try {
            mockMvc.perform(post(BASE_URL + GET_RESERVER_RESERVATIONS)).andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
