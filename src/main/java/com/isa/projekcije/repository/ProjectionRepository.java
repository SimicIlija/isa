package com.isa.projekcije.repository;

import com.isa.projekcije.model.Projection;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ProjectionRepository extends JpaRepository<Projection, Long> {

    Projection save(Projection projection);

    List<Projection> findByShow(Long showId);

    List<Projection> findByAuditorium(Long auditoriumId);

    List<Projection> findByDate(Date date);

    Projection findByTickets(Long ticketId);

    Projection findById(Long id);

    List<Projection> findByDateBetweenAndShowId(Date start, Date end, Long idShow);
}
