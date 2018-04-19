package com.isa.projekcije.converters;

import com.isa.projekcije.model.OnSaleTicket;
import com.isa.projekcije.model.Ticket;
import com.isa.projekcije.model.dto.OnSaleTicketDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class OnSaleTicketToOnSaleTicketDTO implements Converter<OnSaleTicket, OnSaleTicketDTO> {
    @Override
    public OnSaleTicketDTO convert(OnSaleTicket onSaleTicket) {
        if (onSaleTicket == null) {
            return null;
        }
        OnSaleTicketDTO onSaleTicketDTO = new OnSaleTicketDTO();
        onSaleTicketDTO.setId(onSaleTicket.getId());
        onSaleTicketDTO.setIdOnSaleTicket(onSaleTicket.getId());
        onSaleTicketDTO.setDiscount(onSaleTicket.getDiscount());

        if (onSaleTicket.getTicket() != null) {
            Ticket ticket = onSaleTicket.getTicket();
            onSaleTicketDTO.setIdProjection(ticket.getProjection().getId());
            onSaleTicketDTO.setIdTicket(ticket.getId());
            onSaleTicketDTO.setOldPrice(ticket.getPrice().doubleValue());
            onSaleTicketDTO.setShowName(ticket.getProjection().getShow().getName());
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            onSaleTicketDTO.setShowDate(formatter.format(ticket.getProjection().getDate()));
            onSaleTicketDTO.setAuditoriumName(ticket.getProjection().getAuditorium().getName());
            onSaleTicketDTO.setIdSegment(ticket.getSeat().getSegment().getId());
            onSaleTicketDTO.setSegmentName(ticket.getSeat().getSegment().getLabel());
            onSaleTicketDTO.setSeatRow(ticket.getSeat().getRow());
            onSaleTicketDTO.setSeatNumber(ticket.getSeat().getSeatNumber());
        }

        return onSaleTicketDTO;
    }

    public List<OnSaleTicketDTO> convert(List<OnSaleTicket> tickets) {
        List<OnSaleTicketDTO> onSaleTicketDTOList = new ArrayList<OnSaleTicketDTO>();
        for (OnSaleTicket onSaleTicket : tickets) {
            onSaleTicketDTOList.add(convert(onSaleTicket));
        }
        return onSaleTicketDTOList;
    }
}
