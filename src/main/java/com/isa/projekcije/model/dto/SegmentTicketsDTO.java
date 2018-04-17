package com.isa.projekcije.model.dto;

public class SegmentTicketsDTO {
    private Long idSegment;
    private Long idProjection;
    private double price;

    public SegmentTicketsDTO() {
    }

    public SegmentTicketsDTO(Long idSegment, Long idProjection, double price) {
        this.idSegment = idSegment;
        this.idProjection = idProjection;
        this.price = price;
    }

    public Long getIdSegment() {
        return idSegment;
    }

    public void setIdSegment(Long idSegment) {
        this.idSegment = idSegment;
    }

    public Long getIdProjection() {
        return idProjection;
    }

    public void setIdProjection(Long idProjection) {
        this.idProjection = idProjection;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
