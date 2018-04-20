package com.isa.projekcije.repository;

import com.isa.projekcije.model.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InviteRepository extends JpaRepository<Invite, Long> {

    List<Invite> findByIdReservation(long id);


    List<Invite> findByIdInvitedUser(Long idUser);

    Invite findByIdReservationAndIdInvitedUser(long id, long id1);
}
