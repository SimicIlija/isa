package com.isa.projekcije.controller;

import com.isa.projekcije.converters.ProjectionRatingDTOToProjectionRating;
import com.isa.projekcije.converters.ProjectionRatingToProjectionRatingDTO;
import com.isa.projekcije.model.ProjectionRating;
import com.isa.projekcije.model.dto.ProjectionRatingDTO;
import com.isa.projekcije.service.ProjectionRatingService;
import com.isa.projekcije.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/projectionRating")
public class RatingController {

    @Autowired
    private ProjectionRatingService projectionRatingService;

    @Autowired
    private ProjectionRatingDTOToProjectionRating projectionRatingDTOToProjectionRating;

    @Autowired
    private ProjectionRatingToProjectionRatingDTO projectionRatingToProjectionRatingDTO;

    @Autowired
    private UserService userService;

    @RequestMapping(
            value = "/getByProjection/{idProjection}",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> getByProjection(@PathVariable Long idProjection) {
        List<ProjectionRating> projectionRatingList = projectionRatingService.getByProjection(idProjection);
        return new ResponseEntity<>(projectionRatingToProjectionRatingDTO.convert(projectionRatingList), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/addProjectionRating",
            method = RequestMethod.POST
    )
    public ResponseEntity<?> addProjectionRating(@RequestBody ProjectionRatingDTO projectionRatingDTO) {
        if (projectionRatingDTO.getId() != null) {
            ProjectionRating projectionRating = projectionRatingService.findOne(projectionRatingDTO.getId());

            if (projectionRating == null) {
                projectionRating = projectionRatingService.getByUserAndProjection(userService.getCurrentUser().getId(),
                        projectionRatingDTO.getIdProjection());
                if (projectionRating != null) {
                    projectionRating.setProjectionRating(projectionRatingDTO.getProjectionRating());
                    ProjectionRating editedProjectionRating = projectionRatingService.saveRating(projectionRating);
                    return new ResponseEntity<>(projectionRatingToProjectionRatingDTO.convert(editedProjectionRating), HttpStatus.OK);
                }
            }
        }

        ProjectionRating newProjectionRating = projectionRatingService.saveRating(projectionRatingDTOToProjectionRating.convert(projectionRatingDTO));
        return new ResponseEntity<>(projectionRatingToProjectionRatingDTO.convert(newProjectionRating), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/addInstitutionRating",
            method = RequestMethod.POST
    )
    public ResponseEntity<?> addInstitutionRating(@RequestBody ProjectionRatingDTO projectionRatingDTO) {
        ProjectionRating projectionRating = projectionRatingService.findOne(projectionRatingDTO.getId());

        if (projectionRating == null) {
            projectionRating = projectionRatingService.getByUserAndProjection(userService.getCurrentUser().getId(),
                    projectionRatingDTO.getIdProjection());
            if (projectionRating != null) {
                projectionRating.setInstitutionRating(projectionRatingDTO.getInstitutionRating());
                ProjectionRating editedProjectionRating = projectionRatingService.saveRating(projectionRating);
                return new ResponseEntity<>(projectionRatingToProjectionRatingDTO.convert(editedProjectionRating), HttpStatus.OK);
            }
        }

        ProjectionRating newProjectionRating = projectionRatingService.saveRating(projectionRatingDTOToProjectionRating.convert(projectionRatingDTO));
        return new ResponseEntity<>(projectionRatingToProjectionRatingDTO.convert(newProjectionRating), HttpStatus.OK);
    }
}
