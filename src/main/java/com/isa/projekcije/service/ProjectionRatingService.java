package com.isa.projekcije.service;

import com.isa.projekcije.model.ProjectionRating;
import com.isa.projekcije.repository.ProjectionRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectionRatingService {
    @Autowired
    private ProjectionRatingRepository projectionRatingRepository;

    public ProjectionRating findOne(Long id) {
        return projectionRatingRepository.findOne(id);
    }

    public ProjectionRating saveRating(ProjectionRating projectionRating) {
        return projectionRatingRepository.save(projectionRating);
    }

    public List<ProjectionRating> getByProjection(Long idProjection) {
        return projectionRatingRepository.getByProjectionId(idProjection);
    }

    public ProjectionRating getByUserAndProjection(Long idUser, Long idProjection) {
        return projectionRatingRepository.findByUserIdAndProjectionId(idUser, idProjection);
    }

    public List<ProjectionRating> findByInstitution(Long idInstitution) {
        return projectionRatingRepository.findByProjectionAuditoriumInstitutionId(idInstitution);
    }

    public List<ProjectionRating> findByShow(Long idShow) {
        return projectionRatingRepository.findByProjectionShowId(idShow);
    }
}
