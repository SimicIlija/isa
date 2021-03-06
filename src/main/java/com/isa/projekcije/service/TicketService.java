package com.isa.projekcije.service;

import com.isa.projekcije.model.Seat;
import com.isa.projekcije.model.Ticket;
import com.isa.projekcije.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    public Ticket findById(Long id) {
        return ticketRepository.findById(id);
    }

    public List<Ticket> findByPrice(BigDecimal price) {
        return ticketRepository.findByPrice(price);

    }

    public List<Ticket> findByProjectionId(Long idProjection) {
        return ticketRepository.findByProjectionId(idProjection);

    }

    public List<Ticket> findByReserved(boolean reserved) {
        return ticketRepository.findByReserved(reserved);

    }

    public List<Ticket> findBySeat(Seat seat) {
        return ticketRepository.findBySeat(seat);

    }

    public List<Ticket> findByReservationId(Long id) {
        return ticketRepository.findByReservationId(id);

    }

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    @Transactional(readOnly = false)
    public Ticket save(Ticket ticketToAdd) {
        return ticketRepository.save(ticketToAdd);
    }

    public Ticket delete(Long id) {
        Ticket ticket = ticketRepository.findOne(id);
        if (ticket == null) {
            throw new IllegalArgumentException("Tried to delete"
                    + "non-existant ");
        }
        ticketRepository.delete(ticket);
        return ticket;
    }

    public Ticket findByProjectionIdAndSeatSegmentIdAndSeatRowAndSeatSeatNumber(Long idProjection,
                                                                                Long idSegment, int row, int seatNumber) {
        return ticketRepository.findByProjectionIdAndSeatSegmentIdAndSeatRowAndSeatSeatNumber(idProjection,
                idSegment, row, seatNumber);
    }
}
