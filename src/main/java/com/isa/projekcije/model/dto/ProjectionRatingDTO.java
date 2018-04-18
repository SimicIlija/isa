package com.isa.projekcije.model.dto;

public class ProjectionRatingDTO {

    private Long id;
    private Long idUser;
    private Long idProjection;
    private int projectionRating;
    private int institutionRating;

    public ProjectionRatingDTO() {
    }

    public ProjectionRatingDTO(Long idUser, Long idProjection, int projectionRating, int institutionRating) {
        this.idUser = idUser;
        this.idProjection = idProjection;
        this.projectionRating = projectionRating;
        this.institutionRating = institutionRating;
    }

    public ProjectionRatingDTO(Long id, Long idUser, Long idProjection, int projectionRating, int institutionRating) {
        this.id = id;
        this.idUser = idUser;
        this.idProjection = idProjection;
        this.projectionRating = projectionRating;
        this.institutionRating = institutionRating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdProjection() {
        return idProjection;
    }

    public void setIdProjection(Long idProjection) {
        this.idProjection = idProjection;
    }

    public int getProjectionRating() {
        return projectionRating;
    }

    public void setProjectionRating(int projectionRating) {
        this.projectionRating = projectionRating;
    }

    public int getInstitutionRating() {
        return institutionRating;
    }

    public void setInstitutionRating(int institutionRating) {
        this.institutionRating = institutionRating;
    }
}
