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

    public List<Projection> findByDate(Date start, Date end, Long idShow) {
        return projectionRepository.findByDateBetweenAndShowId(start, end, idShow);
    }
}
