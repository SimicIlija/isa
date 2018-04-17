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
import com.isa.projekcije.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private StorageService storageService;

    @Autowired
    private ShowDTOToShowConverter showDTOToShowConverter;

    @Autowired
    private ShowToShowDTOConverter showToShowDTOConverter;

    //aktuelni filmovi/predstave
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

    //svi filmovi/predstave
    @RequestMapping(
            value = "/getAllByInstitution/{idInstitution}",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> getAllByInstitution(@PathVariable Long idInstitution) {
        Institution institution = institutionService.findOne(idInstitution);
        if (institution == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (institution.getShows() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(showToShowDTOConverter.convert(institution.getShows()), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/getById/{id}",
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
            value = "/addShow",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> addShow(@RequestBody ShowDTO showDTO) {
        Show show = showService.save(showDTOToShowConverter.convert(showDTO));
        return new ResponseEntity<>(showToShowDTOConverter.convert(show), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/editShow/{id}",
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
        Show newShow = showService.save(edited);
        return new ResponseEntity<>(showToShowDTOConverter.convert(newShow), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/deleteShow/{id}",
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

    @RequestMapping(value = "addImage/{idShow}/image", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity uploadFile(@PathVariable long idShow, @RequestParam("file") MultipartFile file) {
        try {
            Show show = showService.findById(idShow);
            String imageUrl = storageService.store(file);
            show.setPosterFileName(imageUrl);
            showService.save(show);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "getImage/{idShow}/image", method = RequestMethod.GET)
    public ResponseEntity<Resource> getFile(@PathVariable long idShow) {
        try {
            Show show = showService.findById(idShow);
            String imageUrl = show.getPosterFileName();
            Resource resource = storageService.loadFile(imageUrl);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
