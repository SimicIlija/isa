package com.isa.projekcije.service;

import com.isa.projekcije.model.Institution;
import com.isa.projekcije.repository.InstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstitutionService {

    @Autowired
    private InstitutionRepository institutionRepository;

    public Institution findOne(Long id) {
        return institutionRepository.findOne(id);
    }

    public List<Institution> findAll() {
        return institutionRepository.findAll();
    }

    public List<Institution> getCinemas() {
        return institutionRepository.findByIsCinema(true);
    }

    public List<Institution> getTheatres() {
        return institutionRepository.findByIsCinema(false);
    }

    public Institution save(Institution institution) {
        return institutionRepository.save(institution);
    }

    public Institution delete(Long id) {
        Institution institution = institutionRepository.findOne(id);
        if (institution == null) {
            return null;
        }
        institutionRepository.delete(institution);
        return institution;
    }

}
