package com.isa.projekcije.converters;

import com.isa.projekcije.model.Invite;
import com.isa.projekcije.model.Reservation;
import com.isa.projekcije.model.Ticket;
import com.isa.projekcije.model.User;
import com.isa.projekcije.model.dto.ReservationDTO;
import com.isa.projekcije.service.InviteService;
import com.isa.projekcije.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReservationToReservationDTO implements Converter<Reservation, ReservationDTO> {
    @Autowired
    private InviteService inviteService;
    @Autowired
    private UserService userService;

    @Override
    public ReservationDTO convert(Reservation reservation) {
        if (reservation == null) {

            return null;
        }
        ReservationDTO reservationDTO = new ReservationDTO();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");
        String formattedDate = dateFormat.format(reservation.getDate());
        reservationDTO.setDate(formattedDate);
        reservationDTO.setId(reservation.getId());
        reservationDTO.setIdReserver(reservation.getReserver().getId());
        reservationDTO.setIdProjection(reservation.getProjection().getId());

        List<String> emails = new ArrayList<String>();
        List<User> friends = new ArrayList<User>();
        for (Invite invite : inviteService.findByIdReservation(reservation.getId())) {
            User userFriend = userService.findById(invite.getIdInvitedUser());
            emails.add(userFriend.getEmail());
        }
        reservationDTO.setFriends(emails);

        List<Long> tickets = new ArrayList<Long>();
        for (Ticket t : reservation.getTickets_reserved()) {
            tickets.add(t.getId());
        }
        reservationDTO.setIdSeat(tickets);
        return reservationDTO;
    }

    public List<ReservationDTO> convert(List<Reservation> reservation) {
        List<ReservationDTO> reservationDTOS = new ArrayList<ReservationDTO>();
        for (Reservation reservation1 : reservation) {
            reservationDTOS.add(convert(reservation1));
        }

        return reservationDTOS;
    }
}
