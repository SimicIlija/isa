package com.isa.projekcije.model.dto;

public class SegmentDTO {

    private Long id;
    private String label;
    private boolean closed;
    private Long idAuditorium;

    public SegmentDTO() {
    }

    public SegmentDTO(Long id, String label, boolean closed, Long idAuditorium) {
        this.id = id;
        this.label = label;
        this.closed = closed;
        this.idAuditorium = idAuditorium;
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
