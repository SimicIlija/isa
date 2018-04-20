package com.isa.projekcije.converters;

import com.isa.projekcije.model.Projection;
import com.isa.projekcije.model.ProjectionRating;
import com.isa.projekcije.model.User;
import com.isa.projekcije.model.dto.ProjectionRatingDTO;
import com.isa.projekcije.service.ProjectionService;
import com.isa.projekcije.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectionRatingDTOToProjectionRating implements Converter<ProjectionRatingDTO, ProjectionRating> {

    @Autowired
    private ProjectionService projectionService;

    @Autowired
    private UserService userService;

    @Override
    public ProjectionRating convert(ProjectionRatingDTO projectionRatingDTO) {
        if (projectionRatingDTO == null) {
            return null;
        }
        ProjectionRating projectionRating = new ProjectionRating();
        if (projectionRatingDTO.getIdProjection() != null) {
            Projection projection = projectionService.findById(projectionRatingDTO.getIdProjection());
            projectionRating.setProjection(projection);
        }
        if (projectionRatingDTO.getIdUser() != null) {
            User user = userService.getUserById(projectionRatingDTO.getIdUser());
            projectionRating.setUser(user);
        }
        if (projectionRatingDTO.getId() != null) {
            projectionRating.setId(projectionRatingDTO.getId());
        }
        projectionRating.setProjectionRating(projectionRatingDTO.getProjectionRating());
        projectionRating.setInstitutionRating(projectionRatingDTO.getInstitutionRating());
        return projectionRating;
    }

    public List<ProjectionRating> convert(List<ProjectionRatingDTO> projectionRatingDTOList) {
        List<ProjectionRating> projectionRatings = new ArrayList<ProjectionRating>();
        for (ProjectionRatingDTO projectionRatingDTO : projectionRatingDTOList) {
            projectionRatings.add(convert(projectionRatingDTO));
        }
        return projectionRatings;
    }
}
