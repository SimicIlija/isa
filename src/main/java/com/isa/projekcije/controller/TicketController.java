package com.isa.projekcije.controller;

import com.isa.projekcije.converters.TicketDTOToTicket;
import com.isa.projekcije.converters.TicketToTicketDTO;
import com.isa.projekcije.model.Projection;
import com.isa.projekcije.model.Seat;
import com.isa.projekcije.model.Segment;
import com.isa.projekcije.model.Ticket;
import com.isa.projekcije.model.dto.SegmentTicketsDTO;
import com.isa.projekcije.model.dto.TicketDTO;
import com.isa.projekcije.service.ProjectionService;
import com.isa.projekcije.service.SegmentService;
import com.isa.projekcije.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    SegmentService segmentService;

    @Autowired
    ProjectionService projectionService;

    @Autowired
    private TicketDTOToTicket ticketDTOToTicket;

    @Autowired
    private TicketToTicketDTO ticketToTicketDTO;

    @RequestMapping(
            value = "/{idProjection}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTickets(@PathVariable Long idProjection) {
        List<Ticket> tickets = ticketService.findByProjectionId(idProjection);
        List<TicketDTO> ticketsDTO = ticketToTicketDTO.convert(tickets);
        return new ResponseEntity(ticketsDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN_INST')")
    @RequestMapping(
            value = "/addTicket",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addTicket(@RequestBody TicketDTO ticketToAdd) {

        if (ticketToAdd.getId_projection() == null || ticketToAdd.getId_seat() == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Ticket ticket = ticketDTOToTicket.convert(ticketToAdd);
        Ticket addedTicket = ticketService.save(ticket);
        TicketDTO ticketDTO = ticketToTicketDTO.convert(addedTicket);
        return new ResponseEntity(ticketDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN_INST')")
    @RequestMapping(
            value = "/addTicketArray",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addTicketArray(@RequestBody List<TicketDTO> ticketsDTOList) {
        List<TicketDTO> addedTicketsDTO = new ArrayList<TicketDTO>();
        for (TicketDTO ticketDTO : ticketsDTOList) {
            Ticket ticket = ticketDTOToTicket.convert(ticketDTO);
            try {
                Ticket addedTicket = ticketService.save(ticket);
                addedTicketsDTO.add(ticketToTicketDTO.convert(addedTicket));
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
        }

        return new ResponseEntity(addedTicketsDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN_INST')")
    @RequestMapping(
            value = "/addTicketsForSegment",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addTicketsForSegment(@RequestBody SegmentTicketsDTO segmentTicketsDTO) {
        if (segmentTicketsDTO.getIdSegment() == null || segmentTicketsDTO.getIdProjection() == null) {
            return new ResponseEntity<>("Fill in all fields.", HttpStatus.BAD_REQUEST);
        }
        Segment segment = segmentService.findOne(segmentTicketsDTO.getIdSegment());

        Projection projection = projectionService.findOne(segmentTicketsDTO.getIdProjection());
        if (segment == null || projection == null) {
            return new ResponseEntity<>("Fill in all fields.", HttpStatus.BAD_REQUEST);
        }
        for (Seat seat : segment.getSeats()) {
            Ticket ticket = new Ticket(new BigDecimal(segmentTicketsDTO.getPrice()), seat, projection, false);
            ticketService.save(ticket);
        }
        return new ResponseEntity<>(segmentTicketsDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN_INST')")
    @RequestMapping(
            value = "/editTicketsForSegment",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editTicketsForSegment(@RequestBody SegmentTicketsDTO segmentTicketsDTO) {
        Segment segment = segmentService.findOne(segmentTicketsDTO.getIdSegment());
        Projection projection = projectionService.findOne(segmentTicketsDTO.getIdProjection());
        if (segment == null || projection == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Ticket> tickets = ticketService.findByProjectionId(segmentTicketsDTO.getIdProjection());
        if (tickets == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        for (Ticket ticket : tickets) {
            if (ticket.getSeat().getSegment().getId() == segmentTicketsDTO.getIdSegment()) {
                ticket.setPrice(new BigDecimal(segmentTicketsDTO.getPrice()));
                ticketService.save(ticket);
            }
        }
        return new ResponseEntity<>(segmentTicketsDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN_INST')")
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(
            value = "/deleteTicketsForSegment",
            method = RequestMethod.DELETE
    )
    public ResponseEntity<?> deleteTicketsForSegment(@RequestBody SegmentTicketsDTO segmentTicketsDTO) {
        Segment segment = segmentService.findOne(segmentTicketsDTO.getIdSegment());
        Projection projection = projectionService.findOne(segmentTicketsDTO.getIdProjection());
        if (segment == null || projection == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Ticket> tickets = ticketService.findByProjectionId(segmentTicketsDTO.getIdProjection());
        if (tickets == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        for (Ticket ticket : tickets) {
            if (ticket.getSeat().getSegment().getId() == segmentTicketsDTO.getIdSegment()) {
                if (ticket.isReserved()) {
                    return new ResponseEntity("Some tickets for segment are reserved.", HttpStatus.BAD_REQUEST);
                }
                ticketService.delete(ticket.getId());
            } else {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(segmentTicketsDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN_INST')")
    @RequestMapping(
            value = "/{idTicketToDelete}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteTicket(@PathVariable Long idTicketToDelete) {
        Ticket ticket = ticketService.delete(idTicketToDelete);
        TicketDTO ticketDTO = ticketToTicketDTO.convert(ticket);
        return new ResponseEntity(ticketDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN_INST')")
    @RequestMapping(
            value = "/{idTicket}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeTicket(@PathVariable Long idTicket, @RequestBody TicketDTO changedTicketDTO) {
        Ticket ticket = ticketService.findById(idTicket);
        Ticket changed = ticketDTOToTicket.convert(changedTicketDTO);
        if (changed.getProjection() != null) {
            ticket.setProjection(changed.getProjection());
        }
        if (changed.getSeat() != null) {
            ticket.setSeat(changed.getSeat());
        }
        ticket.setReserved(changed.isReserved());
        ticket.setPrice(changed.getPrice());
        Ticket saved = ticketService.save(ticket);
        TicketDTO ticketDTO = ticketToTicketDTO.convert(saved);

        return new ResponseEntity(ticketDTO, HttpStatus.OK);
    }


}
