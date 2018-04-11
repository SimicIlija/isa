package com.isa.projekcije.controller;

import com.isa.projekcije.converters.ActorDTOToActor;
import com.isa.projekcije.converters.ActorToActorDTO;
import com.isa.projekcije.model.Actor;
import com.isa.projekcije.model.dto.ActorDTO;
import com.isa.projekcije.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/actors")
public class ActorController {

    @Autowired
    private ActorService actorService;

    @Autowired
    private ActorDTOToActor actorDTOToActor;

    @Autowired
    private ActorToActorDTO actorToActorDTO;


    @RequestMapping(
            value = "/addActor",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addActor(@RequestBody ActorDTO actorToAdd) {

        Actor actor = actorDTOToActor.convert(actorToAdd);
        Actor addedActor = actorService.save(actor);
        ActorDTO actorDTO = actorToActorDTO.convert(addedActor);

        return new ResponseEntity(actorDTO, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/{idActorToDelete}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteActor(@PathVariable Long idActorToDelete) {
        Actor actor = actorService.delete(idActorToDelete);
        ActorDTO actorDTO = actorToActorDTO.convert(actor);
        return new ResponseEntity(actorDTO, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/{idActor}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeActor(@PathVariable Long idActor, @RequestBody ActorDTO changedActorDTO) {
        Actor actor = actorService.findById(idActor);
        Actor changed = actorDTOToActor.convert(changedActorDTO);

        actor.setName(changed.getName());
        actor.setLastname(changed.getLastname());


        Actor saved = actorService.save(actor);
        ActorDTO actorDTO = actorToActorDTO.convert(saved);

        return new ResponseEntity(actor, HttpStatus.OK);
    }
}
