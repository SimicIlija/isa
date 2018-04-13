package com.isa.projekcije.service;

import com.isa.projekcije.model.Auditorium;
import com.isa.projekcije.repository.AuditoriumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditoriumService {

    @Autowired
    private AuditoriumRepository auditoriumRepository;

    public Auditorium findOne(Long id) {
        return auditoriumRepository.findOne(id);
    }

    public List<Auditorium> findAll() {
        return auditoriumRepository.findAll();
    }

    public List<Auditorium> findByInstitution(Long idInstitution) {
        return auditoriumRepository.findByInstitutionId(idInstitution);
    }

    public Auditorium save(Auditorium auditorium) {
        return auditoriumRepository.save(auditorium);
    }

    public Auditorium delete(Long id) {
        Auditorium auditorium = auditoriumRepository.findOne(id);
        if (auditorium == null) {
            return null;
        }
        auditoriumRepository.delete(auditorium);
        return auditorium;
    }
}
