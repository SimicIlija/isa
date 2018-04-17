package com.isa.projekcije.converters;

import com.isa.projekcije.model.Reservation;
import com.isa.projekcije.model.Ticket;
import com.isa.projekcije.model.dto.ReservationDTO;
import com.isa.projekcije.repository.ReservationsRepository;
import com.isa.projekcije.repository.UserRepository;
import com.isa.projekcije.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Component
public class ReservationDTOToReservation implements Converter<ReservationDTO, Reservation> {


    @Autowired
    UserService userService;

    @Override
    public Reservation convert(ReservationDTO reservationDTO) {
        if (reservationDTO == null) {
            return null;
        }

        Reservation reservation = new Reservation();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");
        Date date = null;
        try {
            date = formatter.parse(reservationDTO.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        reservation.setDate(date);
        reservation.setReserver(userService.findById(reservationDTO.getIdReserver()));
        reservation.setTickets_reserved(new ArrayList<Ticket>());


        return reservation;

    }
}
