package com.isa.projekcije.repository;

import com.isa.projekcije.model.ProjectionRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectionRatingRepository extends JpaRepository<ProjectionRating, Long> {

    public List<ProjectionRating> getByProjectionId(Long idProjection);

    public List<ProjectionRating> getByUserId(Long idUser);

    public ProjectionRating findByUserIdAndProjectionId(Long idUser, Long idProjection);

    public List<ProjectionRating> findByProjectionAuditoriumInstitutionId(Long idInsitution);

    public List<ProjectionRating> findByProjectionShowId(Long idShow);
}
