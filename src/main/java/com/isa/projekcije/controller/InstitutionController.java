package com.isa.projekcije.controller;

import com.isa.projekcije.converters.InstitutionDTOToInstitutionConverter;
import com.isa.projekcije.converters.InstitutionToInstitutionDTOConverter;
import com.isa.projekcije.model.Institution;
import com.isa.projekcije.model.InstitutionAdmin;
import com.isa.projekcije.model.Role;
import com.isa.projekcije.model.User;
import com.isa.projekcije.model.dto.InstitutionDTO;
import com.isa.projekcije.service.InstitutionService;
import com.isa.projekcije.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/institution")
public class InstitutionController {

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private InstitutionToInstitutionDTOConverter institutionToInstitutionDTOConverter;

    @Autowired
    private InstitutionDTOToInstitutionConverter institutionDTOToInstitutionConverter;

    @Autowired
    private UserService userService;

    @RequestMapping(
            value = "/getById/{idInstitution}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getByInstitution(@PathVariable Long idInstitution) {
        Institution institution = institutionService.findOne(idInstitution);
        if (institution == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(institutionToInstitutionDTOConverter.convert(institution), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/getInstitutions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getInstitutions() {
        List<Institution> institutions = institutionService.findAll();
        return new ResponseEntity<>(institutionToInstitutionDTOConverter.convert(institutions), HttpStatus.OK);
    }

    @RequestMapping(
            value = "getCinemas",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> getCinemas() {
        List<Institution> institutions = institutionService.getCinemas();
        return new ResponseEntity<>(institutionToInstitutionDTOConverter.convert(institutions), HttpStatus.OK);
    }

    @RequestMapping(
            value = "getTheatres",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> getTheatres() {
        List<Institution> institutions = institutionService.getTheatres();
        return new ResponseEntity<>(institutionToInstitutionDTOConverter.convert(institutions), HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = "application/json"
    )
    public ResponseEntity<?> addInstitution(@RequestBody InstitutionDTO institutionDTO) {
        Institution newInstitution = institutionService.save(institutionDTOToInstitutionConverter.convert(institutionDTO));
        return new ResponseEntity<>(institutionToInstitutionDTOConverter.convert(newInstitution), HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT,
            consumes = "application/json"
    )
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody InstitutionDTO institutionDTO) {
        Institution edited = institutionService.findOne(id);
        if (edited == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        edited.setName(institutionDTO.getName());
        edited.setLongitude(institutionDTO.getLongitude());
        edited.setLatitude(institutionDTO.getLatitude());
        edited.setDescription(institutionDTO.getDescription());
        Institution newInstitution = institutionService.save(edited);
        return new ResponseEntity<>(institutionToInstitutionDTOConverter.convert(newInstitution), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE
    )
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Institution deleted = institutionService.delete(id);
        if (deleted == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(institutionToInstitutionDTOConverter.convert(deleted), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/getInstitutionsByAdmin",
            method = RequestMethod.GET
    )
    public ResponseEntity getInstitutionsByAdmin() {
        User loggedIn = userService.getCurrentUser();
        if (loggedIn.getRole().equals(Role.ADMIN_INST)) {
            if (((InstitutionAdmin) loggedIn).getInstitutions() != null) {
                return new ResponseEntity(institutionToInstitutionDTOConverter.convert(((InstitutionAdmin) loggedIn).getInstitutions()), HttpStatus.OK);
            }
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

}
