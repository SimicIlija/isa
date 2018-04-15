package com.isa.projekcije.model.fanzone;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isa.projekcije.model.User;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class UserProps {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserPropsState state;

    @ManyToOne(fetch = FetchType.EAGER)
    private User creator;

    private String imageUrl;

    @OneToMany(mappedBy = "userProps")
    @JsonIgnore
    private Set<Bid> bids;

    public UserProps() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UserPropsState getState() {
        return state;
    }

    public void setState(UserPropsState state) {
        this.state = state;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Set<Bid> getBids() {
        return bids;
    }

    public void setBids(Set<Bid> bids) {
        this.bids = bids;
    }
}
