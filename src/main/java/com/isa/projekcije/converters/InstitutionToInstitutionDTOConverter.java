package com.isa.projekcije.converters;

import com.isa.projekcije.model.Institution;
import com.isa.projekcije.model.dto.InstitutionDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InstitutionToInstitutionDTOConverter implements Converter<Institution, InstitutionDTO> {

    @Override
    public InstitutionDTO convert(Institution source) {
        if (source == null) {
            return null;
        }
        return new InstitutionDTO(source.getId(), source.getName(), source.getLongitude(), source.getLatitude(), source.getDescription(), source.isCinema());
    }

    public List<InstitutionDTO> convert(List<Institution> source) {
        List<InstitutionDTO> institutionDTOList = new ArrayList<InstitutionDTO>();
        for (Institution institution : source) {
            institutionDTOList.add(convert(institution));
        }
        return institutionDTOList;
    }

}
