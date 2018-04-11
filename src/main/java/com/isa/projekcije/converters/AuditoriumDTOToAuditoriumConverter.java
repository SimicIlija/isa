package com.isa.projekcije.converters;

import com.isa.projekcije.model.Auditorium;
import com.isa.projekcije.model.Institution;
import com.isa.projekcije.model.Projection;
import com.isa.projekcije.model.Segment;
import com.isa.projekcije.model.dto.AuditoriumDTO;
import com.isa.projekcije.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AuditoriumDTOToAuditoriumConverter implements Converter<AuditoriumDTO, Auditorium> {

    @Autowired
    private InstitutionService institutionService;

    @Override
    public Auditorium convert(AuditoriumDTO auditoriumDTO) {
        if (auditoriumDTO == null) {
            return null;
        }
        Auditorium auditorium = new Auditorium();
        auditorium.setName(auditoriumDTO.getName());
        auditorium.setProjections(new ArrayList<Projection>());
        auditorium.setSegments(new ArrayList<Segment>());
        if (auditoriumDTO.getIdInstitution() != null) {
            Institution institution = institutionService.findOne(auditoriumDTO.getIdInstitution());
            auditorium.setInstitution(institution);
        }
        return auditorium;
    }
}
