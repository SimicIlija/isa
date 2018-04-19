package com.isa.projekcije.model.dto;

public class OnSaleTicketDTO {
    private Long id;
    private Long idProjection;
    private Long idOnSaleTicket;
    private Long idTicket;
    private double oldPrice;
    private int discount;
    private String showName;
    private String showDate;
    private String auditoriumName;
    private Long idSegment;
    private String segmentName;
    private int seatRow;
    private int seatNumber;

    public OnSaleTicketDTO() {
    }

    public OnSaleTicketDTO(Long idProjection, Long idOnSaleTicket, Long idTicket, double oldPrice, int discount,
                           String showName, String showDate, String auditoriumName, Long idSegment,
                           String segmentName, int seatRow, int seatNumber) {
        this.idProjection = idProjection;
        this.idOnSaleTicket = idOnSaleTicket;
        this.idTicket = idTicket;
        this.oldPrice = oldPrice;
        this.discount = discount;
        this.showName = showName;
        this.showDate = showDate;
        this.auditoriumName = auditoriumName;
        this.idSegment = idSegment;
        this.segmentName = segmentName;
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
    }

    public OnSaleTicketDTO(Long idProjection, int discount, Long idSegment, int seatRow, int seatNumber) {
        this.idProjection = idProjection;
        this.discount = discount;
        this.idSegment = idSegment;
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
    }

    public Long getIdProjection() {
        return idProjection;
    }

    public void setIdProjection(Long idProjection) {
        this.idProjection = idProjection;
    }

    public Long getIdOnSaleTicket() {
        return idOnSaleTicket;
    }

    public void setIdOnSaleTicket(Long idOnSaleTicket) {
        this.idOnSaleTicket = idOnSaleTicket;
    }

    public Long getIdTicket() {
        return idTicket;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIdTicket(Long idTicket) {
        this.idTicket = idTicket;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public String getAuditoriumName() {
        return auditoriumName;
    }

    public void setAuditoriumName(String auditoriumName) {
        this.auditoriumName = auditoriumName;
    }

    public Long getIdSegment() {
        return idSegment;
    }

    public void setIdSegment(Long idSegment) {
        this.idSegment = idSegment;
    }

    public String getSegmentName() {
        return segmentName;
    }

    public void setSegmentName(String segmentName) {
        this.segmentName = segmentName;
    }

    public int getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(int seatRow) {
        this.seatRow = seatRow;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }
}
