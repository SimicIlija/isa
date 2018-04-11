package com.isa.projekcije.repository;

import com.isa.projekcije.model.Auditorium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditoriumRepository extends JpaRepository<Auditorium, Long> {
    List<Auditorium> findByInstitutionId(Long idInstitution);
}
