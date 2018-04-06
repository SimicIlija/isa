package com.isa.projekcije.repository;

import com.isa.projekcije.model.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {
    <E extends Institution> Optional<E> findById(String id);
}
