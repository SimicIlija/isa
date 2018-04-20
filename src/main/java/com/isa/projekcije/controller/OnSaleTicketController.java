package com.isa.projekcije.controller;

import com.isa.projekcije.converters.OnSaleTicketToOnSaleTicketDTO;
import com.isa.projekcije.converters.ReservationToReservationDTO;
import com.isa.projekcije.model.*;
import com.isa.projekcije.model.dto.OnSaleTicketDTO;
import com.isa.projekcije.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/onSaleTicket")
public class OnSaleTicketController {

    @Autowired
    private OnSaleTicketService onSaleTicketService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private SegmentService segmentService;

    @Autowired
    private ProjectionService projectionService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReservationsService reservationsService;

    @Autowired
    private ReservationToReservationDTO reservationToReservationDTO;

    @Autowired
    private OnSaleTicketToOnSaleTicketDTO onSaleTicketToOnSaleTicketDTO;

    @PreAuthorize("hasAuthority('ADMIN_INST')")
    @RequestMapping(
            value = "/addOnSaleTicket",
            method = RequestMethod.POST
    )
    public ResponseEntity<?> addOnSaleTicket(@RequestBody OnSaleTicketDTO onSaleTicketDTO) {
        Segment segment = segmentService.findOne(onSaleTicketDTO.getIdSegment());
        if (segment == null) {
            return new ResponseEntity<>("Segment does't exist.",
                    HttpStatus.BAD_REQUEST);
        }
        if (segment.getRowCount() < onSaleTicketDTO.getSeatRow()) {
            int rowCount = segment.getRowCount() + 1;
            return new ResponseEntity<>("Row count must be smaller than " + rowCount,
                    HttpStatus.BAD_REQUEST);
        }
        if (segment.getSeatsInRowCount() < onSaleTicketDTO.getSeatNumber()) {
            int seatCount = segment.getSeatsInRowCount() + 1;
            return new ResponseEntity<>("Seat number must be smaller than "
                    + seatCount, HttpStatus.BAD_REQUEST);
        }
        Ticket ticket = ticketService.findByProjectionIdAndSeatSegmentIdAndSeatRowAndSeatSeatNumber(
                onSaleTicketDTO.getIdProjection(), onSaleTicketDTO.getIdSegment(),
                onSaleTicketDTO.getSeatRow(), onSaleTicketDTO.getSeatNumber());
        if (ticket == null) {
            return new ResponseEntity<>("Segment is not available for selected projection!",
                    HttpStatus.BAD_REQUEST);
        }
        if (ticket.isReserved()) {
            return new ResponseEntity<>("Ticket already reserved!", HttpStatus.BAD_REQUEST);
        }

        if (onSaleTicketDTO.getDiscount() > 100 || onSaleTicketDTO.getDiscount() < 1) {
            return new ResponseEntity<>("Discount must be between 0 and 100.", HttpStatus.BAD_REQUEST);
        }
        if (ticket.getProjection().getDate().before(new Date())) {
            return new ResponseEntity<>("Projection outdated.", HttpStatus.BAD_REQUEST);
        }
        OnSaleTicket onSaleTicket = new OnSaleTicket(ticket, onSaleTicketDTO.getDiscount());
        try {
            onSaleTicketService.save(onSaleTicket);
        } catch (Exception e) {
            return new ResponseEntity<>("Ticket already added.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(onSaleTicketToOnSaleTicketDTO.convert(onSaleTicket), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/getOnSaleTickets/{idProjection}/{idSegment}",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> getOnSaleTickets(@PathVariable Long idProjection, @PathVariable Long idSegment) {
        List<OnSaleTicket> onSaleTickets = onSaleTicketService.findByTicketProjectionIdAndTicketSeatSegmentId(idProjection, idSegment);
        return new ResponseEntity<>(onSaleTicketToOnSaleTicketDTO.convert(onSaleTickets), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN_INST')")
    @RequestMapping(
            value = "/editOnSaleTicket/{idOnSaleTicket}",
            method = RequestMethod.PUT
    )
    public ResponseEntity<?> addOnSaleTicket(@PathVariable Long idOnSaleTicket, @RequestBody OnSaleTicketDTO onSaleTicketDTO) {
        OnSaleTicket onSaleTicket = onSaleTicketService.findOne(idOnSaleTicket);
        if (onSaleTicket == null) {
            return new ResponseEntity<>("Ticket does't exist.",
                    HttpStatus.BAD_REQUEST);
        }
        if (onSaleTicket.getTicket().isReserved()) {
            return new ResponseEntity<>("Ticket already reserved!",
                    HttpStatus.BAD_REQUEST);
        }
        if (onSaleTicketDTO.getDiscount() > 100 || onSaleTicketDTO.getDiscount() < 1) {
            return new ResponseEntity<>("Discount must be between 0 and 100.", HttpStatus.BAD_REQUEST);
        }
        onSaleTicket.setDiscount(onSaleTicketDTO.getDiscount());
        try {
            OnSaleTicket edited = onSaleTicketService.save(onSaleTicket);
            return new ResponseEntity<>(onSaleTicketToOnSaleTicketDTO.convert(edited), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Ticket already added.", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN_INST')")
    @RequestMapping(
            value = "/deleteOnSaleTicket/{idOnSaleTicker}",
            method = RequestMethod.DELETE
    )
    public ResponseEntity<?> deleteOnSaleTicket(@PathVariable Long idOnSaleTicker) {
        OnSaleTicket toDelete = onSaleTicketService.findOne(idOnSaleTicker);
        if (toDelete == null) {
            return new ResponseEntity<>("Ticket not found.", HttpStatus.BAD_REQUEST);
        }
        if (toDelete.getTicket().isReserved()) {
            return new ResponseEntity<>("Ticket already reserved.", HttpStatus.BAD_REQUEST);
        }
        OnSaleTicket deleted = onSaleTicketService.delete(idOnSaleTicker);
        return new ResponseEntity<>(onSaleTicketToOnSaleTicketDTO.convert(deleted), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/getOnSaleTicketsByInstitution/{idInstitution}",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> getOnSaleTicketsByInstitution(@PathVariable Long idInstitution) {
        List<OnSaleTicket> onSaleTickets = onSaleTicketService.findByInstitutionAndTicketReserved(idInstitution, false);

        List<OnSaleTicketDTO> onSaleTicketDTOS = new ArrayList<OnSaleTicketDTO>();
        for (OnSaleTicket onSaleTicket : onSaleTickets) {
            Date now = new Date();
            if (onSaleTicket.getTicket().getProjection().getDate().after(now)) {
                onSaleTicketDTOS.add(onSaleTicketToOnSaleTicketDTO.convert(onSaleTicket));
            }
        }

        return new ResponseEntity<>(onSaleTicketDTOS, HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/reserveOnSaleTicket/{idOnSaleTicket}",
            method = RequestMethod.POST
    )
    public ResponseEntity<?> reserveOnSaleTicket(@PathVariable Long idOnSaleTicket) {
        OnSaleTicket onSaleTicket = onSaleTicketService.findOne(idOnSaleTicket);
        if (onSaleTicket == null) {
            return new ResponseEntity<>("Ticket is no longer available.", HttpStatus.NOT_FOUND);
        }
        Ticket ticket = onSaleTicket.getTicket();
        if (ticket.isReserved()) {
            return new ResponseEntity<>("Ticket already reserved.", HttpStatus.NOT_FOUND);
        }
        Reservation reservation = new Reservation();
        Projection projection = onSaleTicket.getTicket().getProjection();
        reservation.setProjection(projection);
        reservation.setDate(new Date());
        reservation.setReserver(userService.getCurrentUser());
        List<Ticket> tickets = new ArrayList<Ticket>();
        tickets.add(ticket);
        reservation.setTickets_reserved(tickets);
        reservation.setInvited_friends(new ArrayList<User>());
        reservation.setConfirmed_users(new ArrayList<User>());

        ticket.setReserved(true);
        ticket.setReservation(reservation);
        reservationsService.save(reservation);
        ticketService.save(ticket);

        return new ResponseEntity<>(reservationToReservationDTO.convert(reservation), HttpStatus.OK);
    }

}
