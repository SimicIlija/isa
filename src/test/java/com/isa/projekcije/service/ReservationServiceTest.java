package com.isa.projekcije.service;

import com.isa.projekcije.ProjekcijeApplicationTests;
import com.isa.projekcije.constants.ReservationConstrants;
import com.isa.projekcije.model.Reservation;
import com.isa.projekcije.model.Ticket;
import com.isa.projekcije.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ReservationServiceTest extends ProjekcijeApplicationTests {

    @Autowired
    ReservationsService reservationsService;

    @Autowired
    TicketService ticketService;

    @Test
    public void findByReserverIdShouldReturnEmpty() {

        List<Reservation> reservations = reservationsService.findByReserverId(ReservationConstrants.DB_USER_WITH_NO_RESERVATIONS);
        assertThat(reservations).isEmpty();
    }

    @Test
    public void findByReserverIdShouldReturnList() {

        List<Reservation> reservations = reservationsService.findByReserverId(ReservationConstrants.DB_USER_WITH_RESERVATIONS);
        assertThat(reservations).isNotEmpty();
    }

    @Test
    public void deleteNonExistingReservationShouldReturnNull() {

        Reservation reservation = reservationsService.delete(ReservationConstrants.DB_RESERVATION_TO_DELETE_INVALID);
        assertThat(reservation).isNull();
    }

    @Test
    @Transactional
    @Rollback(true)
    public void deleteExistingReservationShouldReturnReservation() {

        Reservation reservationFound = reservationsService.findById(ReservationConstrants.DB_RESERVATION_TO_DELETE);

        List<Ticket> ticketsRemove = ticketService.findByReservationId(reservationFound.getId());
        for (Ticket ticket : ticketsRemove) {

            ticket.setReservation(null);
            ticket.setReserved(false);
            Ticket saved = ticketService.save(ticket);
        }


        Reservation reservation = reservationsService.delete(ReservationConstrants.DB_RESERVATION_TO_DELETE);
        assertThat(reservation).isNotNull();
        assertThat(reservation.getProjection().getId()).isEqualTo(reservationFound.getProjection().getId());
        assertThat(reservation.getId()).isEqualTo(reservationFound.getId());
        assertThat(reservation.getReserver().getId()).isEqualTo(reservationFound.getReserver().getId());
        assertThat(reservation.getDate()).isEqualTo(reservationFound.getDate());

    }
}
