package com.isa.projekcije.repository;

import com.isa.projekcije.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationsRepository extends JpaRepository<Reservation, Long> {
}
