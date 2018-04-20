package com.isa.projekcije.service;

import com.isa.projekcije.ProjekcijeApplicationTests;
import com.isa.projekcije.constants.RatingConstants;
import com.isa.projekcije.model.ProjectionRating;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ProjectionRatingServiceTest extends ProjekcijeApplicationTests {

    @Autowired
    private ProjectionRatingService projectionRatingService;

    @Autowired
    private ProjectionService projectionService;

    @Test
    public void testFindOne() {
        ProjectionRating projectionRating = projectionRatingService.findOne(RatingConstants.DB_ID);
        assertThat(projectionRating).isNotNull();
        assertThat(projectionRating.getId()).isEqualTo(RatingConstants.DB_ID);
        assertThat(projectionRating.getInstitutionRating()).isEqualTo(RatingConstants.DB_INSTITUTION_RATING);
        assertThat(projectionRating.getProjectionRating()).isEqualTo(RatingConstants.DB_PROJECTION_RATING);
        assertThat(projectionRating.getProjection().getId()).isEqualTo(RatingConstants.DB_ID_PROJECTION);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testAdd() {
        ProjectionRating projectionRating = new ProjectionRating();
        projectionRating.setProjection(projectionService.findById(RatingConstants.NEW_ID_PROJECTION));
        projectionRating.setProjectionRating(RatingConstants.NEW_PROJECTION_RATING);
        projectionRating.setInstitutionRating(RatingConstants.NEW_INSTITUTION_RATING);

        ProjectionRating dbProjectionRating = projectionRatingService.saveRating(projectionRating);
        assertThat(dbProjectionRating).isNotNull();
    }

    @Test
    public void testGetByProjection() {
        List<ProjectionRating> projectionRatings = projectionRatingService.getByProjection(RatingConstants.DB_ID_PROJECTION);
        assertThat(projectionRatings).hasSize(RatingConstants.BY_PROJECTION_COUNT);
    }
}
