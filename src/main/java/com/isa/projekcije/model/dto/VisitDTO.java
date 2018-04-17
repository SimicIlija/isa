package com.isa.projekcije.model.dto;

public class VisitDTO {
    private Long institutionId;
    private String institutionName;
    private Long showId;
    private String showName;
    private String date;
    private Long projectionId;
    private Long idRating;
    private int myProjectionRating;
    private int myInstitutionRating;
    private Long idUser;

    public VisitDTO() {
    }

    public VisitDTO(Long institutionId, String institutionName, Long showId, String showName, String date,
                    Long projectionId, int myProjectionRating, int myInstitutionRating, Long idUser) {
        this.institutionId = institutionId;
        this.institutionName = institutionName;
        this.showId = showId;
        this.showName = showName;
        this.date = date;
        this.projectionId = projectionId;
        this.myProjectionRating = myProjectionRating;
        this.myInstitutionRating = myInstitutionRating;
        this.idUser = idUser;
    }

    public VisitDTO(Long institutionId, String institutionName, Long showId, String showName, Long projectionId, Long idUser) {
        this.institutionId = institutionId;
        this.institutionName = institutionName;
        this.showId = showId;
        this.showName = showName;
        this.projectionId = projectionId;
        this.idUser = idUser;
    }

    public Long getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(Long institutionId) {
        this.institutionId = institutionId;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public Long getShowId() {
        return showId;
    }

    public void setShowId(Long showId) {
        this.showId = showId;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public Long getProjectionId() {
        return projectionId;
    }

    public void setProjectionId(Long projectionId) {
        this.projectionId = projectionId;
    }

    public int getMyProjectionRating() {
        return myProjectionRating;
    }

    public void setMyProjectionRating(int myProjectionRating) {
        this.myProjectionRating = myProjectionRating;
    }

    public int getMyInstitutionRating() {
        return myInstitutionRating;
    }

    public void setMyInstitutionRating(int myInstitutionRating) {
        this.myInstitutionRating = myInstitutionRating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getIdRating() {
        return idRating;
    }

    public void setIdRating(Long idRating) {
        this.idRating = idRating;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
}
