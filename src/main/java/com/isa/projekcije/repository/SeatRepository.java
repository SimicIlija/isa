package com.isa.projekcije.repository;

import com.isa.projekcije.model.Seat;
import com.isa.projekcije.model.Segment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    Seat save(Seat seat);

    List<Seat> findByRow(int row);

    List<Seat> findBySeatNumber(int seatNumber);

    List<Seat> findBySegment(Segment segment);

    Seat findById(Long id);


}
