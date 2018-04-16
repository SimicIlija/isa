package com.isa.projekcije.model.dto;

public class SegmentDTO {

    private Long id;
    private String label;
    private boolean closed;
    private Long idAuditorium;
    private int rowCount;
    private int seatsInRowCount;

    public SegmentDTO() {
    }


    public SegmentDTO(Long id, String label, boolean closed, Long idAuditorium, int rowCount, int seatsInRowCount) {
        this.id = id;
        this.label = label;
        this.closed = closed;
        this.idAuditorium = idAuditorium;
        this.rowCount = rowCount;
        this.seatsInRowCount = seatsInRowCount;
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

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public Long getIdAuditorium() {
        return idAuditorium;
    }

    public void setIdAuditorium(Long idAuditorium) {
        this.idAuditorium = idAuditorium;
    }
}
