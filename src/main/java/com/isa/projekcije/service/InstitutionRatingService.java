package com.isa.projekcije.service;

import com.isa.projekcije.model.InstitutionRating;
import com.isa.projekcije.repository.InstitutionRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstitutionRatingService {
    @Autowired
    private InstitutionRatingRepository institutionRatingRepository;

    public InstitutionRating saveRating(InstitutionRating institutionRating) {
        return institutionRatingRepository.save(institutionRating);
    }

    public List<InstitutionRating> getByProjection(Long idInstitution) {
        return institutionRatingRepository.getByInstitutionId(idInstitution);
    }

    public List<InstitutionRating> getByUser(Long idUser) {
        return institutionRatingRepository.getByUserId(idUser);
    }

    public InstitutionRating getByUserAndProjection(Long idUser, Long idInstitution) {
        return institutionRatingRepository.findByUserIdAndInstitutionId(idUser, idInstitution);
    }
}
