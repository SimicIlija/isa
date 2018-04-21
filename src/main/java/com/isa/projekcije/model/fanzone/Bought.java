package com.isa.projekcije.model.fanzone;

import com.isa.projekcije.model.User;

import javax.persistence.*;

@Entity
public class Bought {
    @EmbeddedId
    private BoughtId boughtId = new BoughtId();

    private int amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("buyerId")
    private User buyer;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("themePropsId")
    private ThemeProps themeProps;

    public Bought() {
    }

    public BoughtId getBoughtId() {
        return boughtId;
    }

    public void setBoughtId(BoughtId boughtId) {
        this.boughtId = boughtId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public ThemeProps getThemeProps() {
        return themeProps;
    }

    public void setThemeProps(ThemeProps themeProps) {
        this.themeProps = themeProps;
    }
}
