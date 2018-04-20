package com.isa.projekcije.model.dto;

import com.isa.projekcije.model.fanzone.ThemeProps;

public class ThemePropsGetDto {
    private long id;
    private String name;
    private String description;
    private int amount;
    private double price;
    private String imageUrl;

    public ThemePropsGetDto() {
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ThemePropsGetDto(ThemeProps themeProps) {
        this.id = themeProps.getId();
        this.amount = themeProps.getAmount();
        this.description = themeProps.getDescription();
        this.imageUrl = themeProps.getImageUrl();
        this.name = themeProps.getName();
        this.price = themeProps.getPrice();
    }
}
