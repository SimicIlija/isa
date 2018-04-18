package com.isa.projekcije.converters;

import com.isa.projekcije.model.ProjectionRating;
import com.isa.projekcije.model.dto.ProjectionRatingDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectionRatingToProjectionRatingDTO implements Converter<ProjectionRating, ProjectionRatingDTO> {
    @Override
    public ProjectionRatingDTO convert(ProjectionRating projectionRating) {
        if (projectionRating == null) {
            return null;
        }
        return new ProjectionRatingDTO(projectionRating.getId(), projectionRating.getUser().getId(), projectionRating.getProjection().getId(), projectionRating.getProjectionRating(), projectionRating.getInstitutionRating());
    }

    public List<ProjectionRatingDTO> convert(List<ProjectionRating> projectionRatings) {
        List<ProjectionRatingDTO> projectionRatingDTOList = new ArrayList<ProjectionRatingDTO>();
        for (ProjectionRating projectionRating : projectionRatings) {
            projectionRatingDTOList.add(convert(projectionRating));
        }
        return projectionRatingDTOList;
    }
}
