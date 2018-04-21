package com.isa.projekcije.converters;

import com.isa.projekcije.model.Reservation;
import com.isa.projekcije.model.Show;
import com.isa.projekcije.model.Ticket;
import com.isa.projekcije.model.User;
import com.isa.projekcije.model.dto.ReservationDTO;
import com.isa.projekcije.repository.ReservationsRepository;
import com.isa.projekcije.repository.UserRepository;
import com.isa.projekcije.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeBodyPart;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ReservationDTOToReservation implements Converter<ReservationDTO, Reservation> {


    @Autowired
    UserService userService;
    @Autowired
    ProjectionService projectionService;
    @Autowired
    TicketService ticketService;
    @Autowired
    ReservationsService reservationsService;
    @Autowired
    EmailService emailService;

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
        reservation.setReserver(userService.getCurrentUser());
        reservation.setProjection(projectionService.findById(reservationDTO.getIdProjection()));
        if (reservation.getProjection() == null) {
            return null;
        }


        reservation.setTickets_reserved(new ArrayList<Ticket>());

        Reservation res = reservationsService.save(reservation);

        for (Ticket t : reservation.getTickets_reserved()) {
            t.setReservation(reservation);
            Ticket ticket1 = ticketService.save(t);
        }


        return res;

    }
}
