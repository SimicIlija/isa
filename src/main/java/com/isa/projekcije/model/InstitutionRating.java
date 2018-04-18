package com.isa.projekcije.model;

import javax.persistence.*;

@Entity
public class InstitutionRating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_institution")
    private Institution institution;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private User user;

    private int institutionRating;

    public InstitutionRating() {
    }

    public InstitutionRating(Institution institution, User user, int institutionRating) {
        this.institution = institution;
        this.user = user;
        this.institutionRating = institutionRating;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getInstitutionRating() {
        return institutionRating;
    }

    public void setInstitutionRating(int institutionRating) {
        this.institutionRating = institutionRating;
    }
}
