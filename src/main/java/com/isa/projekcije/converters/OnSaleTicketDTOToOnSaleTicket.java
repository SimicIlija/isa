package com.isa.projekcije.converters;

import com.isa.projekcije.model.OnSaleTicket;
import com.isa.projekcije.model.Ticket;
import com.isa.projekcije.model.dto.OnSaleTicketDTO;
import com.isa.projekcije.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OnSaleTicketDTOToOnSaleTicket implements Converter<OnSaleTicketDTO, OnSaleTicket> {

    @Autowired
    private TicketService ticketService;

    @Override
    public OnSaleTicket convert(OnSaleTicketDTO onSaleTicketDTO) {
        if (onSaleTicketDTO == null) {
            return null;
        }
        OnSaleTicket onSaleTicket = new OnSaleTicket();
        onSaleTicket.setDiscount(onSaleTicketDTO.getDiscount());

        if (onSaleTicketDTO.getIdTicket() != null) {
            Ticket ticket = ticketService.findById(onSaleTicketDTO.getIdTicket());
            onSaleTicket.setTicket(ticket);
        }

        return onSaleTicket;
    }
}
