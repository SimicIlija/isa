package com.isa.projekcije.controller;

import com.isa.projekcije.converters.TicketDTOToTicket;
import com.isa.projekcije.converters.TicketToTicketDTO;
import com.isa.projekcije.model.Ticket;
import com.isa.projekcije.model.User;
import com.isa.projekcije.model.dto.LoginDTO;
import com.isa.projekcije.model.dto.TicketDTO;
import com.isa.projekcije.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

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

    @RequestMapping(
            value = "/addTicket",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addTicket(@RequestBody TicketDTO ticketToAdd) {

        Ticket ticket = ticketDTOToTicket.convert(ticketToAdd);
        Ticket addedTicket = ticketService.save(ticket);
        TicketDTO ticketDTO = ticketToTicketDTO.convert(addedTicket);
        return new ResponseEntity(ticketDTO, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/{idTicketToDelete}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteTicket(@PathVariable Long idTicketToDelete) {
        Ticket ticket = ticketService.delete(idTicketToDelete);
        TicketDTO ticketDTO = ticketToTicketDTO.convert(ticket);
        return new ResponseEntity(ticketDTO, HttpStatus.OK);
    }

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
