package com.isa.projekcije.converters;

import com.isa.projekcije.model.Projection;
import com.isa.projekcije.model.Seat;
import com.isa.projekcije.model.Ticket;
import com.isa.projekcije.model.dto.TicketDTO;
import com.isa.projekcije.service.ProjectionService;
import com.isa.projekcije.service.SeatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class TicketDTOToTicket implements Converter<TicketDTO, Ticket> {

    @Autowired
    private SeatService seatService;

    @Autowired
    private ProjectionService projectionService;


    @Override
    public Ticket convert(TicketDTO ticketDTO) {
        if (ticketDTO == null) {
            return null;
        }

        Ticket ticket = new Ticket();


        ticket.setPrice(ticketDTO.getPrice());
        ticket.setReserved(ticketDTO.getReserved());


        if (ticketDTO.getId_seat() != null) {
            Seat seat = seatService.findOne(ticketDTO.getId_seat());

            ticket.setSeat(seat);
        }

        if (ticketDTO.getId_projection() != null) {
            Projection projection = projectionService.findOne(ticketDTO.getId_projection());

            ticket.setProjection(projection);
        }

        return ticket;
    }

    public List<Ticket> convert(List<TicketDTO> source) {

        List<Ticket> tickets = new ArrayList<Ticket>();
        for (TicketDTO ticketDTO : source) {
            tickets.add(convert(ticketDTO));
        }

        return tickets;
    }
}
