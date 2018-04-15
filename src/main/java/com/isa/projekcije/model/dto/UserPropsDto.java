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

    public UserProps createUserProps(User creator) {
        UserProps userProps = new UserProps();
        userProps.setState(UserPropsState.DENIED);
        userProps.setName(this.name);
        userProps.setDescription(this.description);
        userProps.setEndDate(this.endDate);
        userProps.setCreator(creator);
        return userProps;
    }
}
