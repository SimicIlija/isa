package com.isa.projekcije.converters;


import com.isa.projekcije.model.Seat;
import com.isa.projekcije.model.dto.SeatDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SeatToSeatDTO implements Converter<Seat, SeatDTO> {

    @Override
    public SeatDTO convert(Seat seat) {
        if (seat == null) {
            return null;
        }

        SeatDTO seatDTO = new SeatDTO();
        seatDTO.setRow(seat.getRow());
        seatDTO.setSeatNumber(seat.getSeatNumber());
        seatDTO.setId(seat.getId());

        if (seat.getSegment() != null) {
            //fali deo za segment
        }

        return seatDTO;
    }

    public List<SeatDTO> convert(List<Seat> source) {

        List<SeatDTO> seatsDTO = new ArrayList<SeatDTO>();
        for (Seat seat : source) {
            seatsDTO.add(convert(seat));
        }

        return seatsDTO;
    }
}
