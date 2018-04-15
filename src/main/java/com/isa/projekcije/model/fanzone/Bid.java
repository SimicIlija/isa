package com.isa.projekcije.model.fanzone;

import com.isa.projekcije.model.User;

import javax.persistence.*;

@Entity
public class Bid {
    @EmbeddedId
    private BidId bidId;

    private double price;

    @Enumerated(EnumType.STRING)
    private BidState bidState;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userPropsId")
    private UserProps userProps;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("bidderId")
    private User bidder;

    public Bid() {
    }

    public BidId getBidId() {
        return bidId;
    }

    public void setBidId(BidId bidId) {
        this.bidId = bidId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public BidState getBidState() {
        return bidState;
    }

    public void setBidState(BidState bidState) {
        this.bidState = bidState;
    }

    public UserProps getUserProps() {
        return userProps;
    }

    public void setUserProps(UserProps userProps) {
        this.userProps = userProps;
    }

    public User getBidder() {
        return bidder;
    }

    public void setBidder(User bidder) {
        this.bidder = bidder;
    }
}
