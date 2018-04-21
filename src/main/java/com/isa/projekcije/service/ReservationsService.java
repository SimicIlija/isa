package com.isa.projekcije.service;

import com.isa.projekcije.model.Reservation;
import com.isa.projekcije.repository.ReservationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationsService {
    @Autowired
    private ReservationsRepository reservationsRepository;


    public Reservation save(Reservation reservation) {
        return reservationsRepository.save(reservation);
    }

    public List<Reservation> findByReserverId(Long currentUser) {
        return reservationsRepository.findByReserverId(currentUser);
    }

    public Reservation delete(Long idToDelete) {
        Reservation reservation = reservationsRepository.findOne(idToDelete);
        if (reservation == null) {
            return null;
        }
        reservationsRepository.delete(reservation.getId());
        return reservation;
    }


    public Reservation findById(Long idReservation) {
        return reservationsRepository.findById(idReservation);
    }


    public List<Reservation> findAll() {
        return reservationsRepository.findAll();
    }

    public List<Reservation> findByInstitution(Long idInstitution) {
        return reservationsRepository.findByProjectionAuditoriumInstitutionId(idInstitution);
    }
}
