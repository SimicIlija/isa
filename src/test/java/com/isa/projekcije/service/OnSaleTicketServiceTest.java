package com.isa.projekcije.service;

import com.isa.projekcije.ProjekcijeApplicationTests;
import com.isa.projekcije.constants.OnSaleTicketConstants;
import com.isa.projekcije.model.OnSaleTicket;
import com.isa.projekcije.model.Ticket;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class OnSaleTicketServiceTest extends ProjekcijeApplicationTests {

    @Autowired
    private OnSaleTicketService onSaleTicketService;

    @Autowired
    private TicketService ticketService;

    @Test
    @Transactional
    @Rollback(true)
    public void testAdd() {
        OnSaleTicket onSaleTicket = new OnSaleTicket();
        Ticket ticket = ticketService.findById(OnSaleTicketConstants.NEW_ID_TICKET);
        onSaleTicket.setTicket(ticket);

        OnSaleTicket dbOnSaleTicket = onSaleTicketService.save(onSaleTicket);
        assertThat(dbOnSaleTicket).isNotNull();
    }

    @Test
    public void testFindByInstitutionAndReserved() {
        List<OnSaleTicket> onSaleTickets = onSaleTicketService.findByInstitutionAndTicketReserved(OnSaleTicketConstants.DB_ID_INSTITUTION, false);
        assertThat(onSaleTickets.size()).isEqualTo(OnSaleTicketConstants.DB_BY_INSTITUTION_AND_RESERVED_COUNT);
    }

    @Test
    public void testFindOne() {
        OnSaleTicket onSaleTicket = onSaleTicketService.findOne(OnSaleTicketConstants.DB_ID);
        assertThat(onSaleTicket).isNotNull();
        assertThat(onSaleTicket.getId()).isEqualTo(OnSaleTicketConstants.DB_ID);
        assertThat(onSaleTicket.getTicket().getId()).isEqualTo(OnSaleTicketConstants.DB_ID_TICKET);
        assertThat(onSaleTicket.getDiscount()).isEqualTo(OnSaleTicketConstants.DB_DISCOUNT);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDelete() {
        onSaleTicketService.delete(OnSaleTicketConstants.DB_ID);
        OnSaleTicket onSaleTicket = onSaleTicketService.findOne(OnSaleTicketConstants.DB_ID);
        assertThat(onSaleTicket).isNull();
    }
}
