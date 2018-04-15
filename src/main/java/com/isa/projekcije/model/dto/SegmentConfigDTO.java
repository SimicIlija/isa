package com.isa.projekcije.model.dto;

import java.util.ArrayList;
import java.util.List;

public class SegmentConfigDTO {
    private Long id;
    private String label;
    private int rowCount;
    private int seatsInRowCount;
    private List<SeatTicketDTO> seatTicketDTOList;

    public SegmentConfigDTO() {
        this.seatTicketDTOList = new ArrayList<>();
    }

    public SegmentConfigDTO(Long id, String label, int rowCount, int seatsInRowCount, List<SeatTicketDTO> seatTicketDTOList) {
        this.id = id;
        this.label = label;
        this.rowCount = rowCount;
        this.seatsInRowCount = seatsInRowCount;
        this.seatTicketDTOList = seatTicketDTOList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<SeatTicketDTO> getSeatTicketDTOList() {
        return seatTicketDTOList;
    }

    public void setSeatTicketDTOList(List<SeatTicketDTO> seatTicketDTOList) {
        this.seatTicketDTOList = seatTicketDTOList;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getSeatsInRowCount() {
        return seatsInRowCount;
    }

    public void setSeatsInRowCount(int seatsInRowCount) {
        this.seatsInRowCount = seatsInRowCount;
    }
}
