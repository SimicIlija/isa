package com.isa.projekcije.converters;

import com.isa.projekcije.model.Actor;
import com.isa.projekcije.model.Show;
import com.isa.projekcije.model.dto.ActorDTO;
import com.isa.projekcije.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ActorDTOToActor implements Converter<ActorDTO, Actor> {

    @Autowired
    private ShowService showService;

    @Override
    public Actor convert(ActorDTO actorDTO) {
        if (actorDTO == null) {
            return null;
        }

        Actor actor = new Actor();
        actor.setLastname(actorDTO.getLastname());
        actor.setName(actorDTO.getName());
        if (actorDTO.getIdShow() != null) {
            Show show = showService.findOne(actorDTO.getIdShow());
            actor.setShow(show);
        }
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
