package com.isa.projekcije.controller;

import com.isa.projekcije.converters.ReservationDTOToReservation;
import com.isa.projekcije.model.Reservation;
import com.isa.projekcije.model.dto.ReservationDTO;
import com.isa.projekcije.service.ReservationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {

    @Autowired
    private ReservationsService reservationsService;

    @Autowired
    private ReservationDTOToReservation reservationDTOToReservation;

    @RequestMapping(
            value = "/reserveSeat/{id}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> reserveSeat(@RequestBody ReservationDTO reservationDTO) {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");
        String formattedDate = dateFormat.format(date);
        reservationDTO.setDate(formattedDate);
        Reservation reservation = reservationDTOToReservation.convert(reservationDTO);
        // Reservation saved = reservationsService.save(reservation);
        return new ResponseEntity(reservation, HttpStatus.OK);
    }

}
