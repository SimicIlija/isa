package com.isa.projekcije.model.dto;

import java.util.List;

public class ChartDTO {

    private List<ChartValueDTO> values;

    public ChartDTO() {
    }

    public ChartDTO(List<ChartValueDTO> values) {
        this.values = values;
    }

    public List<ChartValueDTO> getValues() {
        return values;
    }

    public void setValues(List<ChartValueDTO> values) {
        this.values = values;
    }
}
