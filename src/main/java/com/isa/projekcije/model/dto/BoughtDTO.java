package com.isa.projekcije.model.dto;

import com.isa.projekcije.model.fanzone.Bought;

public class BoughtDTO {
    private String name;
    private int amount;
    private double price;

    public BoughtDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public static BoughtDTO createFromBought(Bought bought) {
        BoughtDTO boughtDTO = new BoughtDTO();
        boughtDTO.setName(bought.getThemeProps().getName());
        boughtDTO.setAmount(bought.getAmount());
        boughtDTO.setPrice(bought.getThemeProps().getPrice());
        return boughtDTO;
    }
}
