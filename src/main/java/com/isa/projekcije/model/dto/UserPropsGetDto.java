package com.isa.projekcije.model.dto;

import com.isa.projekcije.model.fanzone.UserProps;
import com.isa.projekcije.model.fanzone.UserPropsState;

import java.util.Date;

public class UserPropsGetDto {
    private long id;

    private String name;

    private String description;

    private Date endDate;

    private UserPropsState state;

    private String creatorName;

    private String imageUrl;

    public UserPropsGetDto() {
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

    public UserPropsState getState() {
        return state;
    }

    public void setState(UserPropsState state) {
        this.state = state;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static UserPropsGetDto createGetDtoFromUserProps(UserProps userProps) {
        UserPropsGetDto userPropsGetDto = new UserPropsGetDto();
        userPropsGetDto.setDescription(userProps.getDescription());
        userPropsGetDto.setName(userProps.getName());
        userPropsGetDto.setEndDate(userProps.getEndDate());
        userPropsGetDto.setId(userProps.getId());
        userPropsGetDto.setImageUrl(userProps.getImageUrl());
        userPropsGetDto.setCreatorName(userProps.getCreator().getEmail());
        userPropsGetDto.setState(userProps.getState());
        return userPropsGetDto;
    }
}
