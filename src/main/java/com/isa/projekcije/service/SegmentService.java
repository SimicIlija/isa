package com.isa.projekcije.service;

import com.isa.projekcije.model.Segment;
import com.isa.projekcije.repository.SegmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SegmentService {

    @Autowired
    private SegmentRepository segmentRepository;

    public Segment findOne(Long id) {
        return segmentRepository.findOne(id);
    }

    public List<Segment> findByAuditorium(Long idAuditorium) {
        return segmentRepository.findByAuditoriumId(idAuditorium);
    }

    public Segment save(Segment segment) {
        return segmentRepository.save(segment);
    }

    public Segment delete(Long id) {
        Segment segment = segmentRepository.findOne(id);
        if (segment == null) {
            return null;
        }
        segmentRepository.delete(segment);
        return segment;
    }

}
