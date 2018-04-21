package com.isa.projekcije.controller;

import com.isa.projekcije.converters.ReservationDTOToReservation;
import com.isa.projekcije.converters.ReservationToReservationDTO;
import com.isa.projekcije.model.*;
import com.isa.projekcije.model.dto.ReservationDTO;
import com.isa.projekcije.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {

    @Autowired
    private ReservationsService reservationsService;

    @Autowired
    private ReservationDTOToReservation reservationDTOToReservation;

    @Autowired
    private ReservationToReservationDTO reservationToReservationDTO;

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;
    @Autowired
    private InviteService inviteService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private ProjectionService projectionService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/createReservation",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> reserveSeat(@RequestBody ReservationDTO reservationDTO) {

        if (reservationDTO.getIdSeat().size() < reservationDTO.getFriends().size() + 1 || reservationDTO.getIdSeat().size() == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");
        String formattedDate = dateFormat.format(date);
        reservationDTO.setDate(formattedDate);

        Reservation reservation = reservationDTOToReservation.convert(reservationDTO);
        if (reservation == null) {
            return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
        }
        //Reservation saved = reservationsService.save(reservation);
        List<User> invitedFriends = new ArrayList<User>();

        Show show = reservation.getProjection().getShow();
        for (String email : reservationDTO.getFriends()) {
            User friend = userService.findByEmail(email);

            //provera prijateljstva
            Friendship existingFriendship1 = friendshipService.findBySenderIdAndReceiverId(reservation.getReserver().getId(), friend.getId());
            Friendship existingFriendship2 = friendshipService.findBySenderIdAndReceiverId(friend.getId(), reservation.getReserver().getId());
            if (existingFriendship1 == null && existingFriendship2 == null) {
                return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
            }
            //kreira se novi poziv za svakog prijatelja
            Invite invite = new Invite(reservation.getId(), friend.getId());

            List<Ticket> tickets = new ArrayList<Ticket>();
            for (Long id : reservationDTO.getIdSeat()) {
                Ticket ticket = ticketService.findById(id);
                ticket.setReserved(true);
                ticket.setReservation(reservation);
                ticketService.save(ticket);
                tickets.add(ticket);
            }
            reservation.setTickets_reserved(tickets);

            List<Invite> existingInvites = inviteService.findByIdInvitedUser(friend.getId());
            for (Invite existingInvite : existingInvites) {
                Reservation existingReservation = reservationsService.findById(existingInvite.getIdReservation());
                Projection existingProjection = existingReservation.getProjection();
                Date existingDate = existingReservation.getProjection().getDate();
                if (existingDate.equals(reservation.getProjection().getDate()) && existingProjection.getId() == reservation.getProjection().getId()) {
                    return new ResponseEntity(false, HttpStatus.OK);
                }
            }
            Invite saved = inviteService.save(invite);


            //slanje emaila svakom korisniku
            emailService.getMail().setTo(friend.getEmail());
            emailService.getMail().setFrom(emailService.getEnv().getProperty("spring.mail.username"));
            emailService.getMail().setSubject("You have been invited to a projection");
            String text = "Hello " + friend.getFirstName() + ",\n\n" + reservation.getReserver().getFirstName() + " " + reservation.getReserver().getLastName() + " invited you to see " + show.getName() + "\n\n" +
                    "Click on the link to confirm/reject invite: http://localhost:1234/homeForLoggedIn.html";

            emailService.getMail().setText(text);
            try {
                emailService.sendNotificaitionAsync(friend);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        ReservationDTO reservationDTO1 = reservationToReservationDTO.convert(reservation);
        if (reservationDTO1 == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(true, HttpStatus.OK);
    }


    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/getReserverReservations",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getReserverReservations() {
        List<Reservation> reservation = reservationsService.findByReserverId(userService.getCurrentUser().getId());
        List<ReservationDTO> reservationDTOS = reservationToReservationDTO.convert(reservation);
        if (reservationDTOS.size() == 0) {
            return new ResponseEntity(reservationDTOS, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(reservationDTOS, HttpStatus.OK);
    }


    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/cancelReservation/{idToDelete}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cancelReservation(@PathVariable Long idToDelete) {
        Reservation reservation = reservationsService.findById(idToDelete);

        if (reservation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        for (Ticket ticket : reservation.getTickets_reserved()) {
            ticket.setReserved(false);
            ticket.setReservation(null);
            ticketService.save(ticket);
        }
        User loggedIn = userService.getCurrentUser();
        loggedIn.getReservations().remove(reservation);


        //provera vremena otkazivanja rezervacije
        Projection projection = reservation.getProjection();
        Date projectionDate = projection.getDate();
        Long timeProjection = projectionDate.getTime();

        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        Long currentTime = date.getTime();
        if (timeProjection - currentTime <= 30) {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
        reservationsService.delete(idToDelete);

        List<Invite> invitesToDelete = inviteService.findByIdReservation(idToDelete);
        for (Invite invite : invitesToDelete) {
            inviteService.delete(invite.getId());
        }

        ReservationDTO reservationDTO = reservationToReservationDTO.convert(reservation);
        return new ResponseEntity(true, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/getInvites/{idUser}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getInvites(@PathVariable Long idUser) {
        //List<Reservation> reservations = reservationsService.findAll();
        User invited = userService.findById(idUser);
        List<Invite> userInvites = inviteService.findByIdInvitedUser(idUser);

        if (userInvites.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Reservation> toReturn = new ArrayList<Reservation>();

        for (Invite invite : userInvites) {
            if (invite.getConfirmed() == false) {
                toReturn.add(reservationsService.findById(invite.getIdReservation()));
            }
        }

        List<ReservationDTO> reservationDTO = reservationToReservationDTO.convert(toReturn);
        return new ResponseEntity<>(reservationDTO, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/setReserved/{idReservation}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> setReserved(@PathVariable Long idReservation) {
        Reservation reservation = reservationsService.findById(idReservation);
        Ticket free = reservation.getTickets_reserved().get(reservation.getTickets_reserved().size() - 1);
        free.setReserved(false);
        free.setReservation(null);
        Ticket savedTicket = ticketService.save(free);
        reservation.getTickets_reserved().remove(reservation.getTickets_reserved().size() - 1);

        Reservation savedReservation = reservationsService.save(reservation);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }


    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/getConfirmedReservations/{idUser}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getConfirmedReservations(@PathVariable Long idUser) {

        User invited = userService.findById(idUser);
        List<Reservation> confirmedReservations = new ArrayList<Reservation>();
        List<Invite> invites = inviteService.findByIdInvitedUser(invited.getId());

        if (invites != null) {
            for (Invite invite : invites) {
                if (invite.getConfirmed() == true) {
                    confirmedReservations.add(reservationsService.findById(invite.getIdReservation()));
                }
            }
        }
        List<ReservationDTO> reservationDTO = reservationToReservationDTO.convert(confirmedReservations);
        return new ResponseEntity<>(reservationDTO, HttpStatus.OK);
    }

}
