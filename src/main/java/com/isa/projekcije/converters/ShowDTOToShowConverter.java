package com.isa.projekcije.converters;

import com.isa.projekcije.model.Actor;
import com.isa.projekcije.model.Projection;
import com.isa.projekcije.model.Show;
import com.isa.projekcije.model.dto.ShowDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ShowDTOToShowConverter implements Converter<ShowDTO, Show> {
    @Override
    public Show convert(ShowDTO showDTO) {
        if (showDTO == null) {
            return null;
        }
        Show show = new Show();
        show.setName(showDTO.getName());
        show.setGenre(showDTO.getGenre());
        show.setProducer(showDTO.getProducer());
        show.setDuration(showDTO.getDuration());
        show.setPosterFileName(showDTO.getPosterFileName());
        show.setPosterData(showDTO.getPosterData());
        show.setActors(new ArrayList<Actor>());
        show.setProjections(new ArrayList<Projection>());
        return show;
    }
}
