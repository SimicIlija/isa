package com.isa.projekcije.model.dto;

public class ShowActorDTO {

    private Long idShow;
    private Long idActor;

    public ShowActorDTO() {
    }

    public ShowActorDTO(Long idShow, Long idActor) {
        this.idShow = idShow;
        this.idActor = idActor;
    }

    public Long getIdShow() {
        return idShow;
    }

    public void setIdShow(Long idShow) {
        this.idShow = idShow;
    }

    public Long getIdActor() {
        return idActor;
    }

    public void setIdActor(Long idActor) {
        this.idActor = idActor;
    }
}
