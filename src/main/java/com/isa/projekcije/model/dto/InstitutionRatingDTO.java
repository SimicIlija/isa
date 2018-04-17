package com.isa.projekcije.model.dto;

public class InstitutionRatingDTO {

    private Long id;
    private Long idUser;
    private Long idInstitution;
    private int institutionRating;

    public InstitutionRatingDTO() {
    }

    public InstitutionRatingDTO(Long idUser, Long idInstitution, int institutionRating) {
        this.idUser = idUser;
        this.idInstitution = idInstitution;
        this.institutionRating = institutionRating;
    }

    public InstitutionRatingDTO(Long id, Long idUser, Long idInstitution, int institutionRating) {
        this.id = id;
        this.idUser = idUser;
        this.idInstitution = idInstitution;
        this.institutionRating = institutionRating;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdInstitution() {
        return idInstitution;
    }

    public void setIdInstitution(Long idInstitution) {
        this.idInstitution = idInstitution;
    }

    public int getInstitutionRating() {
        return institutionRating;
    }

    public void setInstitutionRating(int institutionRating) {
        this.institutionRating = institutionRating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
