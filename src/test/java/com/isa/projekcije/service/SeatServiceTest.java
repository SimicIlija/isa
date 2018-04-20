package com.isa.projekcije.service;

import com.isa.projekcije.ProjekcijeApplicationTests;
import com.isa.projekcije.constants.SeatConstants;
import com.isa.projekcije.model.Seat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class SeatServiceTest extends ProjekcijeApplicationTests {

    @Autowired
    private SeatService seatService;

    @Autowired
    private SegmentService segmentService;

    @Test
    public void testFindOne() {
        Seat seat = seatService.findById(SeatConstants.DB_ID);
        assertThat(seat).isNotNull();
        assertThat(seat.getId()).isEqualTo(SeatConstants.DB_ID);
        assertThat(seat.getRow()).isEqualTo(SeatConstants.DB_ROW);
        assertThat(seat.getSeatNumber()).isEqualTo(SeatConstants.DB_SEAT_NUMBER);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testAdd() {
        Seat seat = new Seat();
        seat.setSegment(segmentService.findOne(SeatConstants.NEW_SEGMENT_ID));
        seat.setRow(SeatConstants.NEW_ROW);
        seat.setSeatNumber(SeatConstants.NEW_SEAT_NUMBER);

        Seat dbSeat = seatService.save(seat);
        assertThat(dbSeat).isNotNull();
    }


}
