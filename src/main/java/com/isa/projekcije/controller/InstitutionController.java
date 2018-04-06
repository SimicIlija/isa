package com.isa.projekcije.controller;

import com.isa.projekcije.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/institution")
public class InstitutionController {

    @Autowired
    private InstitutionService institutionService;

    /*@RequestMapping(
            value = "/{id_institution}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeInstitution(@PathVariable String id_institution, @RequestBody InstitutionDTO institutionDTO) {

    }*/

}
