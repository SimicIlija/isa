package com.isa.projekcije.model.dto;

public class AuditoriumDTO {

    private Long id;
    private String name;
    private Long idInstitution;

    public AuditoriumDTO() {
    }

    public AuditoriumDTO(Long id, String name, Long idInstitution) {
        this.id = id;
        this.name = name;
        this.idInstitution = idInstitution;
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

    public Long getIdInstitution() {
        return idInstitution;
    }

    public void setIdInstitution(Long idInstitution) {
        this.idInstitution = idInstitution;
    }

}
