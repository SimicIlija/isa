package com.isa.projekcije.model;

import javax.persistence.*;

@Entity
public class ProjectionRating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_projection")
    private Projection projection;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private User user;

    private int projectionRating;

    private int institutionRating;

    public ProjectionRating() {
    }

    public ProjectionRating(Projection projection, User user, int projectionRating, int institutionRating) {
        this.projection = projection;
        this.user = user;
        this.projectionRating = projectionRating;
        this.institutionRating = institutionRating;
    }

    public Projection getProjection() {
        return projection;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setProjection(Projection projection) {
        this.projection = projection;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
