package com.isa.projekcije.controller;

import com.isa.projekcije.converters.ReservationDTOToReservation;
import com.isa.projekcije.converters.ReservationToReservationDTO;
import com.isa.projekcije.model.Projection;
import com.isa.projekcije.model.Reservation;
import com.isa.projekcije.model.Ticket;
import com.isa.projekcije.model.User;
import com.isa.projekcije.model.dto.ReservationDTO;
import com.isa.projekcije.model.dto.UserDTO;
import com.isa.projekcije.service.EmailService;
import com.isa.projekcije.service.ReservationsService;
import com.isa.projekcije.service.TicketService;
import com.isa.projekcije.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.Time;
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


    @RequestMapping(
            value = "/createReservation",
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
        //Reservation saved = reservationsService.save(reservation);

        ReservationDTO reservationDTO1 = reservationToReservationDTO.convert(reservation);

        return new ResponseEntity(reservationDTO1, HttpStatus.OK);
    }


    @RequestMapping(
            value = "/getReserverReservations",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getReserverReservations() {
        List<Reservation> reservation = reservationsService.findByReserverId(userService.getCurrentUser().getId());
        List<ReservationDTO> reservationDTOS = reservationToReservationDTO.convert(reservation);
        return new ResponseEntity(reservationDTOS, HttpStatus.OK);
    }


    @RequestMapping(
            value = "/cancelReservation/{idToDelete}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getReserverReservations(@PathVariable Long idToDelete) {
        Reservation reservation = reservationsService.delete(idToDelete);
        User loggedIn = userService.getCurrentUser();
        loggedIn.getReservations().remove(reservation);

        for (User invited : reservation.getInvited_friends()) {
            invited.getReservation_invited().remove(reservation);
            invited.getReservation_confirmed().remove(reservation);
        }
        Projection projection = reservation.getProjection();
        Date projectionDate = projection.getDate();
        Long timeProjection = projectionDate.getTime();

        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        Long currentTime = date.getTime();
        if (timeProjection - currentTime <= 30) {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }

        ReservationDTO reservationDTO = reservationToReservationDTO.convert(reservation);
        return new ResponseEntity(true, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/getInvites/{idUser}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getInvites(@PathVariable Long idUser) {
        //List<Reservation> reservations = reservationsService.findAll();
        User invited = userService.findById(idUser);
        List<Reservation> toReturn = invited.getReservation_invited();
       /*List<Reservation> toReturn = new ArrayList<Reservation>();
       for(Reservation res : reservations){
            for(User inv : res.getInvited_friends()){
                if(inv.getId() == invited.getId()){
                    toReturn.add(res);
                }
            }
        }*/
        List<ReservationDTO> reservationDTO = reservationToReservationDTO.convert(toReturn);
        return new ResponseEntity<>(reservationDTO, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/decline/{idReservation}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> decline(@PathVariable Long idReservation) {
        Reservation reservation = reservationsService.findById(idReservation);
        User invited = userService.getCurrentUser();
        List<User> userEmails = reservation.getInvited_friends();
        User found = null;
        for (User current : userEmails) {
            if (current.getEmail().equals(invited.getEmail())) {
                found = current;
                break;
            }
        }
        reservation.getInvited_friends().remove(found);

        List<Ticket> tickets = reservation.getTickets_reserved();
        Ticket toRemove = reservation.getTickets_reserved().remove(reservation.getTickets_reserved().size() - 1);


        for (Ticket ticket : ticketService.findAll()) {
            if (ticket.getId() == toRemove.getId()) {
                ticket.setReserved(false);
                Ticket ret = ticketService.save(ticket);
            }
        }

        Reservation saved = reservationsService.save(reservation);
        ReservationDTO reservationDTO = reservationToReservationDTO.convert(saved);
        if (reservationDTO == null) {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/confirm/{idReservation}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> confirm(@PathVariable Long idReservation) {

        User invited = userService.getCurrentUser();


        Reservation removed = reservationsService.findById(idReservation);
        removed.getInvited_friends().remove(invited);
        Reservation r = reservationsService.save(removed);
        invited.getReservation_invited().remove(removed);
        invited.getReservation_confirmed().add(removed);
        User saved = userService.save(invited);
        List<ReservationDTO> reservationDTO = reservationToReservationDTO.convert(saved.getReservation_confirmed());
        return new ResponseEntity<>(true, HttpStatus.OK);
    }


    @RequestMapping(
            value = "/getConfirmedReservations/{idUser}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getConfirmedReservations(@PathVariable Long idUser) {

        User invited = userService.findById(idUser);
        List<Reservation> toReturn = invited.getReservation_confirmed();

        List<ReservationDTO> reservationDTO = reservationToReservationDTO.convert(toReturn);
        return new ResponseEntity<>(reservationDTO, HttpStatus.OK);
    }


}
