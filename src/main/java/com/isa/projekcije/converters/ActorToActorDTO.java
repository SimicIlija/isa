package com.isa.projekcije.converters;


import com.isa.projekcije.model.Actor;
import com.isa.projekcije.model.dto.ActorDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ActorToActorDTO implements Converter<Actor, ActorDTO> {

    @Override
    public ActorDTO convert(Actor actor) {
        if (actor == null) {
            return null;
        }

        ActorDTO actorDTO = new ActorDTO();
        actorDTO.setLastname(actor.getLastname());
        actorDTO.setId(actor.getId());
        actorDTO.setName(actor.getName());
        return actorDTO;
    }

    public List<ActorDTO> convert(List<Actor> source) {

        List<ActorDTO> actorsDTO = new ArrayList<ActorDTO>();
        for (Actor actor : source) {
            actorsDTO.add(convert(actor));
        }

        return actorsDTO;
    }
}
