package com.isa.projekcije.model.dto;

public class ChartValueDTO {

    private String X;
    private int Y;

    public ChartValueDTO() {
    }

    public ChartValueDTO(String X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    public String getX() {
        return X;
    }

    public void setX(String x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }
}
