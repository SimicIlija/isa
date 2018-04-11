package com.isa.projekcije.service;

import com.isa.projekcije.model.Projection;
import com.isa.projekcije.repository.ProjectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProjectionService {

    @Autowired
    private ProjectionRepository projectionRepository;

    public List<Projection> findByShow(Long showId) {
        return projectionRepository.findByShow(showId);
    }

    public List<Projection> findByAuditorium(Long auditoriumId) {
        return projectionRepository.findByAuditorium(auditoriumId);
    }

    public List<Projection> findByDate(Date date) {
        return projectionRepository.findByDate(date);
    }

    public Projection findByTickets(Long ticketId) {
        return projectionRepository.findByTickets(ticketId);
    }

    public Projection findById(Long id) {
        return projectionRepository.findById(id);
    }

    public Projection findOne(Long id_projection) {
        return projectionRepository.findOne(id_projection);
    }

    public Projection save(Projection projection) {
        return projectionRepository.save(projection);
    }

    public Projection delete(Long idProjectionToDelete) {
        Projection projection = projectionRepository.findOne(idProjectionToDelete);
        if (projection == null) {
            throw new IllegalArgumentException("Tried to delete"
                    + "non-existant ");
        }
        projectionRepository.delete(projection);
        return projection;
    }
}
