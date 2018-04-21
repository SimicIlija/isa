package com.isa.projekcije.model.dto;

import com.isa.projekcije.model.fanzone.Bid;
import com.isa.projekcije.model.fanzone.BidState;

import java.util.Date;

public class BidDTO {
    private long userId;
    private long propId;
    private String propsName;
    private String userEmail;
    private Date date;
    private double price;
    private BidState state;

    public BidDTO() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPropId() {
        return propId;
    }

    public void setPropId(long propId) {
        this.propId = propId;
    }

    public String getPropsName() {
        return propsName;
    }

    public void setPropsName(String propsName) {
        this.propsName = propsName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public BidState getState() {
        return state;
    }

    public void setState(BidState state) {
        this.state = state;
    }

    public static BidDTO createFromBid(Bid bid) {
        BidDTO bidDTO = new BidDTO();
        bidDTO.setPropsName(bid.getUserProps().getName());
        bidDTO.setDate(bid.getUserProps().getEndDate());
        bidDTO.setState(bid.getBidState());
        bidDTO.setPrice(bid.getPrice());
        bidDTO.setUserEmail(bid.getBidder().getEmail());
        bidDTO.setPropId(bid.getUserProps().getId());
        bidDTO.setUserId(bid.getBidder().getId());
        return bidDTO;
    }
}
