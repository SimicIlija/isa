package com.isa.projekcije.service;

import com.isa.projekcije.model.Actor;
import com.isa.projekcije.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;

    public Actor save(Actor actor) {
        return actorRepository.save(actor);
    }

    public List<Actor> findByName(String name) {
        return actorRepository.findByName(name);
    }

    public List<Actor> findByLastname(String lastaname) {
        return actorRepository.findByLastname(lastaname);
    }

    public Actor findById(Long id) {
        return actorRepository.findById(id);
    }

    public Actor delete(Long idActorToDelete) {
        Actor actor = actorRepository.findOne(idActorToDelete);
        if (actor == null) {
            throw new IllegalArgumentException("Tried to delete"
                    + "non-existant");
        }
        actorRepository.delete(actor);
        return actor;
    }
}
