package com.isa.projekcije.repository;

import com.isa.projekcije.model.Segment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SegmentRepository extends JpaRepository<Segment, Long> {
    List<Segment> findByAuditoriumId(Long idAuditorium);
}
