package com.isa.projekcije.converters;

import com.isa.projekcije.model.Actor;
import com.isa.projekcije.model.Institution;
import com.isa.projekcije.model.Projection;
import com.isa.projekcije.model.Show;
import com.isa.projekcije.model.dto.ShowDTO;
import com.isa.projekcije.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ShowDTOToShowConverter implements Converter<ShowDTO, Show> {

    @Autowired
    private InstitutionService institutionService;

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
        show.setDescription(showDTO.getDescription());
        if (showDTO.getIdInstitution() != null) {
            Institution institution = institutionService.findOne(showDTO.getIdInstitution());
            show.setInstitution(institution);
        }
        return show;
    }
}
