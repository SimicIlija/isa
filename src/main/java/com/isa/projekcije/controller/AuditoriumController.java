package com.isa.projekcije.controller;

import com.isa.projekcije.converters.AuditoriumDTOToAuditoriumConverter;
import com.isa.projekcije.converters.AuditoriumToAuditoriumDTOConverter;
import com.isa.projekcije.model.Auditorium;
import com.isa.projekcije.model.dto.AuditoriumDTO;
import com.isa.projekcije.service.AuditoriumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/auditorium")
public class AuditoriumController {

    @Autowired
    private AuditoriumService auditoriumService;

    @Autowired
    private AuditoriumToAuditoriumDTOConverter auditoriumToAuditoriumDTOConverter;

    @Autowired
    private AuditoriumDTOToAuditoriumConverter auditoriumDTOToAuditoriumConverter;

    @RequestMapping(
            value = "/getByInstitution/{idInstitution}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getByInstitution(@PathVariable Long idInstitution) {
        List<Auditorium> auditoriumList = auditoriumService.findByInstitution(idInstitution);
        return new ResponseEntity<>(auditoriumToAuditoriumDTOConverter.convert(auditoriumList), HttpStatus.OK);
    }

    @RequestMapping(
            value = "addAuditorium",
            method = RequestMethod.POST,
            consumes = "application/json"
    )
    public ResponseEntity<?> addAuditorium(@RequestBody AuditoriumDTO auditoriumDTO) {
        Auditorium auditorium = auditoriumService.save(auditoriumDTOToAuditoriumConverter.convert(auditoriumDTO));
        return new ResponseEntity<>(auditoriumToAuditoriumDTOConverter.convert(auditorium), HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/editAuditorium/{id}",
            method = RequestMethod.PUT,
            consumes = "application/json"
    )
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody AuditoriumDTO auditoriumDTO) {
        Auditorium edited = auditoriumService.findOne(id);
        if (edited == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        edited.setName(auditoriumDTO.getName());
        Auditorium newAuditorium = auditoriumService.save(edited);
        return new ResponseEntity<>(auditoriumToAuditoriumDTOConverter.convert(newAuditorium), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/deleteAuditorium/{id}",
            method = RequestMethod.DELETE
    )
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Auditorium deleted = auditoriumService.delete(id);
        if (deleted == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(auditoriumToAuditoriumDTOConverter.convert(deleted), HttpStatus.OK);
    }
}
