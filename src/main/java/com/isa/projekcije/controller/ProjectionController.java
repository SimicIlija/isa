package com.isa.projekcije.controller;

import com.isa.projekcije.converters.ProjectionDTOToProjection;
import com.isa.projekcije.converters.ProjectionToProjectionDTO;
import com.isa.projekcije.model.Auditorium;
import com.isa.projekcije.model.Projection;
import com.isa.projekcije.model.Show;
import com.isa.projekcije.model.dto.ProjectionDTO;
import com.isa.projekcije.service.AuditoriumService;
import com.isa.projekcije.service.InstitutionService;
import com.isa.projekcije.service.ProjectionService;
import com.isa.projekcije.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/projections")
public class ProjectionController {

    @Autowired
    private ProjectionService projectionService;

    @Autowired
    private ShowService showService;

    @Autowired
    private AuditoriumService auditoriumService;

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private ProjectionDTOToProjection projectionDTOToProjection;

    @Autowired
    private ProjectionToProjectionDTO projectionToProjectionDTO;

    @RequestMapping(
            value = "/getByShow/{idShow}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET
    )
    public ResponseEntity<?> getByShow(@PathVariable Long idShow) {
        Show show = showService.findOne(idShow);
        if (show == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(projectionToProjectionDTO.convert(show.getProjections()), HttpStatus.OK);
    }

    /*@RequestMapping(
            value = "/getByInstitution/{idInstitution}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET
    )
    public ResponseEntity<?> getByInstitution(@PathVariable Long idInstitution) {
        Institution institution = institutionService.findOne(idInstitution);
        if (institution == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Projection> projections = new ArrayList<Projection>();
        for(Auditorium auditorium : institution.getAuditoriums()) {
            projections.addAll(auditorium.getProjections());
        }
        return new ResponseEntity<>(projectionToProjectionDTO.convert(projections), HttpStatus.OK);
    }*/

    @RequestMapping(
            value = "/getByAuditorium/{idAuditorium}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET
    )
    public ResponseEntity<?> getByAuditorium(@PathVariable Long idAuditorium) {
        Auditorium auditorium = auditoriumService.findOne(idAuditorium);
        if (auditorium == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(projectionToProjectionDTO.convert(auditorium.getProjections()), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/addProjection",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addProjection(@RequestBody ProjectionDTO projectionToAdd) {

        Projection projection = projectionDTOToProjection.convert(projectionToAdd);
        Projection addedProjection = projectionService.save(projection);
        ProjectionDTO projectionDTO = projectionToProjectionDTO.convert(addedProjection);

        return new ResponseEntity(projectionDTO, HttpStatus.OK);
    }


    @RequestMapping(
            value = "/{idProjectionToDelete}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteProjection(@PathVariable Long idProjectionToDelete) {
        Projection projection = projectionService.delete(idProjectionToDelete);
        ProjectionDTO projectionDTO = projectionToProjectionDTO.convert(projection);
        return new ResponseEntity(projectionDTO, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/{idProjection}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeProjection(@PathVariable Long idProjection, @RequestBody ProjectionDTO changedProjectionDTO) {
        Projection projection = projectionService.findById(idProjection);
        Projection changed = projectionDTOToProjection.convert(changedProjectionDTO);

        projection.setDate(changed.getDate());
        if (changed.getAuditorium() != null) {
            projection.setAuditorium(changed.getAuditorium());
        }
        if (changed.getShow() != null) {
            projection.setShow(changed.getShow());
        }

        Projection saved = projectionService.save(projection);
        ProjectionDTO projectionDTO = projectionToProjectionDTO.convert(saved);

        return new ResponseEntity(projectionDTO, HttpStatus.OK);
    }


}
