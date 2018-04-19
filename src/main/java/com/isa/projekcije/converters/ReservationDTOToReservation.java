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


        List<User> invitedFriends = new ArrayList<User>();

        for (String email : reservationDTO.getFriends()) {
            User friend = userService.findByEmail(email);
            invitedFriends.add(friend);
        }
        reservation.setInvited_friends(invitedFriends);

        Show show = reservation.getProjection().getShow();
        for (User user : reservation.getInvited_friends()) {

            emailService.getMail().setTo(user.getEmail());
            emailService.getMail().setFrom(emailService.getEnv().getProperty("spring.mail.username"));
            emailService.getMail().setSubject("You have been invited to a projection");
            String text = "Hello " + user.getFirstName() + ",\n\n" + reservation.getReserver().getFirstName() + " " + reservation.getReserver().getLastName() + " invited you to see " + show.getName() + "\n\n" +
                    "Click on the link to confirm/reject invite: http://localhost:1234/invite.html";

            emailService.getMail().setText(text);
            try {
                emailService.sendNotificaitionAsync(user);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        List<Ticket> tickets = new ArrayList<Ticket>();
        for (Long id : reservationDTO.getIdSeat()) {
            Ticket ticket = ticketService.findById(id);
            ticket.setReserved(true);

            tickets.add(ticket);
        }
        reservation.setTickets_reserved(tickets);
        Reservation res = reservationsService.save(reservation);
        for (Ticket t : reservation.getTickets_reserved()) {
            t.setReservation(reservation);
            Ticket ticket1 = ticketService.save(t);
        }

        User loggedInUser = userService.getCurrentUser();
        loggedInUser.getReservations().add(res);
        User u = userService.save(loggedInUser);
        for (User user : reservation.getInvited_friends()) {
            user.getReservation_invited().add(res);
            User usr = userService.save(user);
        }

        return res;

    }
}
