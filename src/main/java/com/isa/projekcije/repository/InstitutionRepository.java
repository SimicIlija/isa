package com.isa.projekcije.repository;

import com.isa.projekcije.model.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {
    List<Institution> findByIsCinema(Boolean isCinema);
}
