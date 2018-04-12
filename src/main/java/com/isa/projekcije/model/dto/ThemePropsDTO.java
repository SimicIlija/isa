package com.isa.projekcije.model.dto;

import com.isa.projekcije.model.Show;
import com.isa.projekcije.model.fanzone.ThemeProps;

public class ThemePropsDTO {
    private String name;
    private String description;
    private int amount;
    private double price;
    private long showId;

    public ThemePropsDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getShowId() {
        return showId;
    }

    public void setShowId(long showId) {
        this.showId = showId;
    }

    public ThemeProps createThemeProps(Show show) {
        ThemeProps themeProps = new ThemeProps();
        themeProps.setName(name);
        themeProps.setDescription(description);
        themeProps.setAmount(amount);
        themeProps.setPrice(price);
        themeProps.setShow(show);
        return themeProps;
    }

    @Override
    public String toString() {
        return "ThemePropsDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                ", showId=" + showId +
                '}';
    }
}