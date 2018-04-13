package com.isa.projekcije.converters;

import com.isa.projekcije.model.Auditorium;
import com.isa.projekcije.model.Institution;
import com.isa.projekcije.model.dto.InstitutionDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class InstitutionDTOToInstitutionConverter implements Converter<InstitutionDTO, Institution> {

    @Override
    public Institution convert(InstitutionDTO institutionDTO) {
        if (institutionDTO == null) {
            return null;
        }
        Institution institution = new Institution();
        institution.setName(institutionDTO.getName());
        institution.setLongitude(institutionDTO.getLongitude());
        institution.setLatitude(institutionDTO.getLatitude());
        institution.setDescription(institutionDTO.getDescription());
        institution.setAuditoriums(new ArrayList<Auditorium>());
        institution.setCinema(institutionDTO.isCinema());
        return institution;
    }
}
