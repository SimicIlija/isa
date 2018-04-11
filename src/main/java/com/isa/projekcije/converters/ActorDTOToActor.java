package com.isa.projekcije.converters;

import com.isa.projekcije.model.Actor;
import com.isa.projekcije.model.dto.ActorDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ActorDTOToActor implements Converter<ActorDTO, Actor> {


    @Override
    public Actor convert(ActorDTO actorDTO) {
        if (actorDTO == null) {
            return null;
        }

        Actor actor = new Actor();
        actor.setLastname(actorDTO.getLastname());
        actor.setName(actorDTO.getName());
        return actor;
    }

    public List<Actor> convert(List<ActorDTO> source) {

        List<Actor> actors = new ArrayList<Actor>();
        for (ActorDTO actorDTO : source) {
            actors.add(convert(actorDTO));
        }

        return actors;
    }
}
