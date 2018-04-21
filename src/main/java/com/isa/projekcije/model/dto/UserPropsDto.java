package com.isa.projekcije.model.dto;

import com.isa.projekcije.model.User;
import com.isa.projekcije.model.fanzone.UserProps;
import com.isa.projekcije.model.fanzone.UserPropsState;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class UserPropsDto {
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotNull
    private Date endDate;

    private String imageUrl;

    public UserPropsDto() {
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

    public UserProps createUserProps(User creator) {
        UserProps userProps = new UserProps();
        userProps.setState(UserPropsState.UNCHECKED);
        userProps.setName(this.name);
        userProps.setDescription(this.description);
        userProps.setImageUrl(this.imageUrl);
        userProps.setEndDate(this.endDate);
        userProps.setCreator(creator);
        return userProps;
    }
}
