package com.isa.projekcije.model.dto;

public class ActorDTO {
    private Long id;
    private String name;
    private String lastname;
    private Long idShow;

    public ActorDTO() {
    }

    public ActorDTO(Long id, String name, String lastname, Long idShow) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.idShow = idShow;
    }

    public Long getIdShow() {
        return idShow;
    }

    public void setIdShow(Long idShow) {
        this.idShow = idShow;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
