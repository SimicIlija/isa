package com.isa.projekcije.model.fanzone;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isa.projekcije.model.Show;

import javax.persistence.*;


@Entity
public class ThemeProps {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    private int amount;
    private double price;
    private String imageUrl;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Show show;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ThemeProps() {
    }

    @Override
    public String toString() {
        return "ThemeProps{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", show=" + show +
                '}';
    }
}
