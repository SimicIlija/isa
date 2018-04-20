package com.isa.projekcije.service;

import com.isa.projekcije.model.Seat;
import com.isa.projekcije.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    public Seat findById(Long id) {
        return seatRepository.findById(id);
    }

    public Seat findOne(Long seatId) {
        return seatRepository.findOne(seatId);
    }

    public Seat save(Seat seat) {
        return seatRepository.save(seat);
    }

    public Seat delete(Long idSeatToDelete) {
        Seat seat = seatRepository.findOne(idSeatToDelete);
        if (seat == null) {
            throw new IllegalArgumentException("Tried to delete"
                    + "non-existant");
        }
        seatRepository.delete(seat);
        return seat;
    }
}
