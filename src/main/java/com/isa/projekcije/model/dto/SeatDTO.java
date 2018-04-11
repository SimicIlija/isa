package com.isa.projekcije.model.dto;

import com.isa.projekcije.model.Segment;
import org.springframework.stereotype.Component;


public class SeatDTO {
    private Long id;
    private int row;
    private int seatNumber;
    private Long segmentId;

    public SeatDTO() {
    }

    public SeatDTO(Long id, int row, int seatNumber) {
        this.id = id;
        this.row = row;
        this.seatNumber = seatNumber;
    }

    public SeatDTO(Long id, int row, int seatNumber, Long segmentId) {
        this.id = id;
        this.row = row;
        this.seatNumber = seatNumber;
        this.segmentId = segmentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Long getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(Long segmentId) {
        this.segmentId = segmentId;
    }
}
