package com.isa.projekcije.model.dto;

import com.isa.projekcije.model.fanzone.ThemeProps;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class ThemePropsDTO {

    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotNull
    private int amount;
    @NotNull
    private double price;

    private String imageUrl;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ThemeProps createThemeProps() {
        ThemeProps themeProps = new ThemeProps();
        themeProps.setName(name);
        themeProps.setDescription(description);
        themeProps.setAmount(amount);
        themeProps.setPrice(price);
        themeProps.setImageUrl(imageUrl);
        return themeProps;
    }

    @Override
    public String toString() {
        return "ThemePropsDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }
}
