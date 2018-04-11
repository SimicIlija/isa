package com.isa.projekcije.model.dto;

public class ProjectionDTO {
    private Long id;
    private Long id_show;
    private Long id_auditorium;
    private String date;

    public ProjectionDTO() {
    }

    public ProjectionDTO(Long id, Long id_show, String date) {
        this.id = id;
        this.id_show = id_show;
        this.date = date;
    }

    public ProjectionDTO(Long id, Long id_show, Long id_auditorium, String date) {
        this.id = id;
        this.id_show = id_show;
        this.id_auditorium = id_auditorium;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_show() {
        return id_show;
    }

    public void setId_show(Long id_show) {
        this.id_show = id_show;
    }

    public Long getId_auditorium() {
        return id_auditorium;
    }

    public void setId_auditorium(Long id_auditorium) {
        this.id_auditorium = id_auditorium;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
