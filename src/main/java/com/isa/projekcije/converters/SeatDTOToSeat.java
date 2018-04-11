package com.isa.projekcije.converters;


import com.isa.projekcije.model.Seat;
import com.isa.projekcije.model.Ticket;
import com.isa.projekcije.model.dto.SeatDTO;
import com.isa.projekcije.model.dto.TicketDTO;
import com.isa.projekcije.service.SegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SeatDTOToSeat implements Converter<SeatDTO, Seat> {

    @Autowired
    private SegmentService segmentService;

    @Override
    public Seat convert(SeatDTO seatDTO) {
        if (seatDTO == null) {
            return null;
        }

        Seat seat = new Seat();


        seat.setRow(seatDTO.getRow());
        seat.setSeatNumber(seatDTO.getSeatNumber());
        seat.setTickets(new ArrayList<Ticket>());

        if (seatDTO.getSegmentId() != null) {
            seat.setSegment(segmentService.findOne(seatDTO.getSegmentId()));
        }
        return seat;
    }

    public List<Seat> convert(List<SeatDTO> source) {

        List<Seat> seats = new ArrayList<Seat>();
        for (SeatDTO seatDTO : source) {
            seats.add(convert(seatDTO));
        }

        return seats;
    }
}
