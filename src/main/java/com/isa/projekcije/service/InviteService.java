package com.isa.projekcije.service;

import com.isa.projekcije.model.Invite;
import com.isa.projekcije.model.Reservation;
import com.isa.projekcije.repository.InviteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InviteService {

    @Autowired
    private InviteRepository inviteRepository;

    public Invite save(Invite invite) {
        return inviteRepository.save(invite);
    }

    public List<Invite> findByIdReservation(long id) {
        return inviteRepository.findByIdReservation(id);
    }

    public Invite delete(Long id) {
        Invite invite = inviteRepository.findOne(id);
        if (invite == null) {
            return null;
        }
        inviteRepository.delete(invite);
        return invite;
    }

    public List<Invite> findByIdInvitedUser(Long idUser) {
        return inviteRepository.findByIdInvitedUser(idUser);
    }

    public Invite findByIdReservationAndIdInvitedUser(long id, long id1) {
        return inviteRepository.findByIdReservationAndIdInvitedUser(id, id1);
    }
}
