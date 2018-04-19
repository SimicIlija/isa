package com.isa.projekcije.repository;

import com.isa.projekcije.model.Seat;
import com.isa.projekcije.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Ticket save(Ticket ticket);

    List<Ticket> findByPrice(BigDecimal price);

    List<Ticket> findByProjectionId(Long idProjection);

    List<Ticket> findByReserved(boolean reserved);

    List<Ticket> findBySeat(Seat seat);

    Ticket findById(Long id);

    List<Ticket> findAll();

    Ticket findByProjectionIdAndSeatSegmentIdAndSeatRowAndSeatSeatNumber(Long idProjection, Long idSegment, int row, int seatNumber);

}
