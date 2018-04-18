package com.isa.projekcije.repository;

import com.isa.projekcije.model.InstitutionRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstitutionRatingRepository extends JpaRepository<InstitutionRating, Long> {

    public List<InstitutionRating> getByInstitutionId(Long idInstitution);

    public List<InstitutionRating> getByUserId(Long idUser);

    public InstitutionRating findByUserIdAndInstitutionId(Long idUser, Long idInstitution);
}
