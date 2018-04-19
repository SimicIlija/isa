package com.isa.projekcije.repository;

import com.isa.projekcije.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationsRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByReserverId(Long currentUser);

    Reservation findById(Long idReservation);


}
