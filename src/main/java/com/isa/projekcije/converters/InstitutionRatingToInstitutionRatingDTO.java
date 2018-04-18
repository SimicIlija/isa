package com.isa.projekcije.converters;

import com.isa.projekcije.model.InstitutionRating;
import com.isa.projekcije.model.dto.InstitutionRatingDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InstitutionRatingToInstitutionRatingDTO implements Converter<InstitutionRating, InstitutionRatingDTO> {
    @Override
    public InstitutionRatingDTO convert(InstitutionRating institutionRating) {
        if (institutionRating == null) {
            return null;
        }
        return new InstitutionRatingDTO(institutionRating.getId(), institutionRating.getUser().getId(), institutionRating.getInstitution().getId(), institutionRating.getInstitutionRating());
    }

    public List<InstitutionRatingDTO> convert(List<InstitutionRating> institutionRatings) {
        List<InstitutionRatingDTO> institutionRatingDTOList = new ArrayList<InstitutionRatingDTO>();
        for (InstitutionRating institutionRating : institutionRatings) {
            institutionRatingDTOList.add(convert(institutionRating));
        }
        return institutionRatingDTOList;
    }
}
