package com.isa.projekcije.service;

import com.isa.projekcije.model.Institution;
import com.isa.projekcije.repository.InstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstitutionService {

    @Autowired
    private InstitutionRepository institutionRepository;

    private Institution findById(String id) {
        return institutionRepository.findById(id).orElseThrow(() -> new RuntimeException());
    }

}
