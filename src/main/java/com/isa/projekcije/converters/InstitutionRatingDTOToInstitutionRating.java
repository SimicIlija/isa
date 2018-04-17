package com.isa.projekcije.converters;


import com.isa.projekcije.model.Institution;
import com.isa.projekcije.model.InstitutionRating;
import com.isa.projekcije.model.User;
import com.isa.projekcije.model.dto.InstitutionRatingDTO;
import com.isa.projekcije.service.InstitutionService;
import com.isa.projekcije.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InstitutionRatingDTOToInstitutionRating implements Converter<InstitutionRatingDTO, InstitutionRating> {

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private UserService userService;

    @Override
    public InstitutionRating convert(InstitutionRatingDTO institutionRatingDTO) {
        if (institutionRatingDTO == null) {
            return null;
        }
        InstitutionRating institutionRating = new InstitutionRating();
        if (institutionRatingDTO.getIdInstitution() != null) {
            Institution institution = institutionService.findOne(institutionRatingDTO.getIdInstitution());
            institutionRating.setInstitution(institution);
        }
        if (institutionRatingDTO.getIdUser() != null) {
            User user = userService.getUserById(institutionRatingDTO.getIdUser());
            institutionRating.setUser(user);
        }
        institutionRating.setId(institutionRatingDTO.getId());
        institutionRating.setInstitutionRating(institutionRatingDTO.getInstitutionRating());
        return institutionRating;
    }

    public List<InstitutionRating> convert(List<InstitutionRatingDTO> institutionRatingDTOList) {
        List<InstitutionRating> institutionRatings = new ArrayList<InstitutionRating>();
        for (InstitutionRatingDTO institutionRatingDTO : institutionRatingDTOList) {
            institutionRatings.add(convert(institutionRatingDTO));
        }
        return institutionRatings;
    }
}
