package com.isa.projekcije.controller;

import com.isa.projekcije.model.Invite;
import com.isa.projekcije.model.Reservation;
import com.isa.projekcije.model.User;
import com.isa.projekcije.model.dto.ReservationDTO;
import com.isa.projekcije.service.InviteService;
import com.isa.projekcije.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/invites")
public class InviteController {

    @Autowired
    private InviteService inviteService;
    @Autowired
    private UserService userService;

    @RequestMapping(
            value = "/cancelInvites/{idReservation}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getInvites(@PathVariable Long idReservation) {
        List<Invite> invites = inviteService.findByIdReservation(idReservation);
        for (Invite invite : invites) {
            Invite deleted = inviteService.delete(invite.getId());
        }

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/decline/{idReservation}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> decline(@PathVariable Long idReservation) {

        User loggedIn = userService.getCurrentUser();

        Invite invite = inviteService.findByIdReservationAndIdInvitedUser(idReservation, loggedIn.getId());
        if (invite == null) {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
        inviteService.delete(invite.getId());


        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/confirm/{idReservation}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> confirm(@PathVariable Long idReservation) {

        User invited = userService.getCurrentUser();

        List<Invite> invites = inviteService.findByIdReservation(idReservation);
        for (Invite invite : invites) {
            invite.setConfirmed(true);
            Invite savedInvite = inviteService.save(invite);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
