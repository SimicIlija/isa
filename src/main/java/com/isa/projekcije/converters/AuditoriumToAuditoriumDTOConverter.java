package com.isa.projekcije.converters;

import com.isa.projekcije.model.Auditorium;
import com.isa.projekcije.model.dto.AuditoriumDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuditoriumToAuditoriumDTOConverter implements Converter<Auditorium, AuditoriumDTO> {

    @Override
    public AuditoriumDTO convert(Auditorium auditorium) {
        if (auditorium == null) {
            return null;
        }
        return new AuditoriumDTO(auditorium.getId(), auditorium.getName(), auditorium.getInstitution().getId());
    }

    public List<AuditoriumDTO> convert(List<Auditorium> source) {
        List<AuditoriumDTO> auditoriumDTOList = new ArrayList<AuditoriumDTO>();
        for (Auditorium auditorium : source) {
            auditoriumDTOList.add(convert(auditorium));
        }
        return auditoriumDTOList;
    }
}
