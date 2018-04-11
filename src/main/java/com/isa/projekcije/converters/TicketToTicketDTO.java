package com.isa.projekcije.converters;

import com.isa.projekcije.model.Projection;
import com.isa.projekcije.model.Seat;
import com.isa.projekcije.model.Ticket;
import com.isa.projekcije.model.dto.TicketDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TicketToTicketDTO implements Converter<Ticket, TicketDTO> {


    @Override
    public TicketDTO convert(Ticket source) {
        if (source == null) {
            return null;
        }

        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setPrice(source.getPrice());
        ticketDTO.setReserved(source.isReserved());
        ticketDTO.setId(source.getId());
        if (source.getProjection() != null) {
            Projection sourceProjection = source.getProjection();
            Long idProjection = sourceProjection.getId();
            ticketDTO.setId_projection(idProjection);
        }

        if (source.getSeat() != null) {
            Seat sourceSeat = source.getSeat();
            Long idSeat = sourceSeat.getId();
            ticketDTO.setId_seat(idSeat);
        }

        return ticketDTO;
    }

    public List<TicketDTO> convert(List<Ticket> source) {

        List<TicketDTO> ticketsDTO = new ArrayList<TicketDTO>();
        for (Ticket ticket : source) {
            ticketsDTO.add(convert(ticket));
        }

        return ticketsDTO;
    }
}
