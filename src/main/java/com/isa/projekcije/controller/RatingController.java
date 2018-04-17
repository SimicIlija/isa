package com.isa.projekcije.controller;

import com.isa.projekcije.converters.InstitutionRatingDTOToInstitutionRating;
import com.isa.projekcije.converters.InstitutionRatingToInstitutionRatingDTO;
import com.isa.projekcije.converters.ProjectionRatingDTOToProjectionRating;
import com.isa.projekcije.converters.ProjectionRatingToProjectionRatingDTO;
import com.isa.projekcije.model.InstitutionRating;
import com.isa.projekcije.model.ProjectionRating;
import com.isa.projekcije.model.dto.ProjectionRatingDTO;
import com.isa.projekcije.service.InstitutionRatingService;
import com.isa.projekcije.service.ProjectionRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/projectionRating")
public class RatingController {

    @Autowired
    private ProjectionRatingService projectionRatingService;

    @Autowired
    private InstitutionRatingService institutionRatingService;

    @Autowired
    private ProjectionRatingDTOToProjectionRating projectionRatingDTOToProjectionRating;

    @Autowired
    private ProjectionRatingToProjectionRatingDTO projectionRatingToProjectionRatingDTO;

    @Autowired
    private InstitutionRatingToInstitutionRatingDTO institutionRatingToInstitutionRatingDTO;

    @Autowired
    private InstitutionRatingDTOToInstitutionRating institutionRatingDTOToInstitutionRating;

    @RequestMapping(
            value = "/getByProjection/{idProjection}",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> getByProjection(@PathVariable Long idProjection) {
        List<ProjectionRating> projectionRatingList = projectionRatingService.getByProjection(idProjection);
        return new ResponseEntity<>(projectionRatingToProjectionRatingDTO.convert(projectionRatingList), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/getByInstitution/{idInstitution}",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> getByInstitution(@PathVariable Long idInstitution) {
        List<InstitutionRating> institutionRatingList = institutionRatingService.getByProjection(idInstitution);
        return new ResponseEntity<>(institutionRatingToInstitutionRatingDTO.convert(institutionRatingList), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/addProjectionRating",
            method = RequestMethod.POST
    )
    public ResponseEntity<?> addProjectionRating(@RequestBody ProjectionRatingDTO projectionRatingDTO) {
        ProjectionRating projectionRating = projectionRatingService.findOne(projectionRatingDTO.getId());
        if (projectionRating != null) {
            projectionRating.setProjectionRating(projectionRatingDTO.getProjectionRating());
            ProjectionRating editedProjectionRating = projectionRatingService.saveRating(projectionRating);
            return new ResponseEntity<>(projectionRatingToProjectionRatingDTO.convert(editedProjectionRating), HttpStatus.OK);
        }
        ProjectionRating newProjectionRating = projectionRatingService.saveRating(projectionRatingDTOToProjectionRating.convert(projectionRatingDTO));
        return new ResponseEntity<>(projectionRatingToProjectionRatingDTO.convert(newProjectionRating), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/addInstitutionRating",
            method = RequestMethod.POST
    )
    public ResponseEntity<?> addInstitutionRating(@RequestBody ProjectionRatingDTO projectionRatingDTO) {
        ProjectionRating projectionRating = projectionRatingService.findOne(projectionRatingDTO.getId());
        if (projectionRating != null) {
            projectionRating.setInstitutionRating(projectionRatingDTO.getInstitutionRating());
            ProjectionRating editedProjectionRating = projectionRatingService.saveRating(projectionRating);
            return new ResponseEntity<>(projectionRatingToProjectionRatingDTO.convert(editedProjectionRating), HttpStatus.OK);
        }
        ProjectionRating newProjectionRating = projectionRatingService.saveRating(projectionRatingDTOToProjectionRating.convert(projectionRatingDTO));
        return new ResponseEntity<>(projectionRatingToProjectionRatingDTO.convert(newProjectionRating), HttpStatus.OK);
    }
}
