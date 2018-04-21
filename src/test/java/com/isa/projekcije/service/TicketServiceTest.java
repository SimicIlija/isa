package com.isa.projekcije.service;

import com.isa.projekcije.ProjekcijeApplicationTests;
import com.isa.projekcije.constants.TicketConstants;
import com.isa.projekcije.model.Seat;
import com.isa.projekcije.model.Ticket;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class TicketServiceTest extends ProjekcijeApplicationTests {

    @Autowired
    private TicketService ticketService;
    @Autowired
    private SeatService seatService;

    @Test
    public void findByProjectionIdShouldReturnEmpty() {

        List<Ticket> tickets = ticketService.findByProjectionId(TicketConstants.NON_EXISTING_PROJECTION);
        assertThat(tickets).isEmpty();
    }

    @Test
    public void findByProjectionIdShouldReturnTickets() {

        List<Ticket> tickets = ticketService.findByProjectionId(TicketConstants.EXISTING_PROJECTION);
        assertThat(tickets).isNotEmpty();
        assertThat(tickets).hasSize(15);
    }


    @Test
    public void findByReservationIdShouldReturnEmpty() {

        List<Ticket> tickets = ticketService.findByReservationId(TicketConstants.NON_EXISTING_RESERVATION);
        assertThat(tickets).isEmpty();
    }

    @Test
    public void findByReservationIdShouldReturnTickets() {

        List<Ticket> tickets = ticketService.findByReservationId(TicketConstants.EXISTING_RESERVATION);
        assertThat(tickets).isNotEmpty();
        assertThat(tickets).hasSize(1);
    }


    @Test
    public void findBySeatIdShouldReturnEmpty() {

        Seat seat = seatService.findById(TicketConstants.NON_EXISTING_SEAT);
        List<Ticket> tickets = ticketService.findBySeat(seat);
        assertThat(tickets).isEmpty();
    }

    @Test
    public void findBySeatIdShouldReturnTickets() {
        Seat seat = seatService.findById(TicketConstants.EXISTING_SEAT);

        List<Ticket> tickets = ticketService.findBySeat(seat);
        assertThat(tickets).isNotEmpty();
        assertThat(tickets).hasSize(1);
    }


}
