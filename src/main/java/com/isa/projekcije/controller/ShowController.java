package com.isa.projekcije.controller;

import com.isa.projekcije.converters.ShowDTOToShowConverter;
import com.isa.projekcije.converters.ShowToShowDTOConverter;
import com.isa.projekcije.model.Actor;
import com.isa.projekcije.model.Institution;
import com.isa.projekcije.model.Projection;
import com.isa.projekcije.model.Show;
import com.isa.projekcije.model.dto.ShowActorDTO;
import com.isa.projekcije.model.dto.ShowDTO;
import com.isa.projekcije.service.ActorService;
import com.isa.projekcije.service.InstitutionService;
import com.isa.projekcije.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/show")
public class ShowController {

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private ShowService showService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private ShowDTOToShowConverter showDTOToShowConverter;

    @Autowired
    private ShowToShowDTOConverter showToShowDTOConverter;

    @RequestMapping(
            value = "/getByInstitution/{idInstitution}",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> getByInstitution(@PathVariable Long idInstitution) {
        Institution institution = institutionService.findOne(idInstitution);
        List<Show> shows = new ArrayList<Show>();
        for (Show show : institution.getShows()) {
            for (Projection projection : show.getProjections()) {
                Date projectionDate = projection.getDate();
                Date todayDate = new Date();
                SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
                String strCurrDate = sdfDate.format(todayDate);
                try {
                    todayDate = new SimpleDateFormat("dd/MM/yyyy").parse(strCurrDate);
                    if (projectionDate.before(todayDate))
                        continue;
                    shows.add(show);
                    break;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return new ResponseEntity<>(showToShowDTOConverter.convert(shows), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Show show = showService.findOne(id);
        if (show == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(showToShowDTOConverter.convert(show), HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> addShow(@RequestBody ShowDTO showDTO) {
        Show show = showService.save(showDTOToShowConverter.convert(showDTO));
        return new ResponseEntity<>(showToShowDTOConverter.convert(show), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody ShowDTO showDTO) {
        Show edited = showService.findOne(id);
        if (edited == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        edited.setName(showDTO.getName());
        edited.setGenre(showDTO.getGenre());
        edited.setProducer(showDTO.getProducer());
        edited.setDuration(showDTO.getDuration());
        edited.setPosterFileName(showDTO.getPosterFileName());
        edited.setPosterData(showDTO.getPosterData());
        Show newShow = showService.save(edited);
        return new ResponseEntity<>(showToShowDTOConverter.convert(newShow), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE
    )
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Show deleted = showService.delete(id);
        if (deleted == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(showToShowDTOConverter.convert(deleted), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/addActor",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST
    )
    public ResponseEntity<?> addActorToShow(@RequestBody ShowActorDTO showActorDTO) {
        Show show = showService.findOne(showActorDTO.getIdShow());
        Actor actor = actorService.findById(showActorDTO.getIdActor());
        if ((show == null) || (actor == null)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        show.getActors().add(actor);
        Show saved = showService.save(show);
        return new ResponseEntity<>(showToShowDTOConverter.convert(saved), HttpStatus.OK);
    }
}
