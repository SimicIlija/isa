package com.isa.projekcije.repository;


import com.isa.projekcije.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ActorRepository extends JpaRepository<Actor, Long> {
    Actor save(Actor actor);

    List<Actor> findByName(String name);

    List<Actor> findByLastname(String lastaname);

    Actor findById(Long id);

}
