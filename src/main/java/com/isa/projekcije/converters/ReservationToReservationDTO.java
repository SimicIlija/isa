package com.isa.projekcije.converters;

import com.isa.projekcije.model.Reservation;
import com.isa.projekcije.model.Ticket;
import com.isa.projekcije.model.User;
import com.isa.projekcije.model.dto.ReservationDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReservationToReservationDTO implements Converter<Reservation, ReservationDTO> {
    @Override
    public ReservationDTO convert(Reservation reservation) {
        if (reservation == null) {

            return null;
        }
        ReservationDTO reservationDTO = new ReservationDTO();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");
        String formattedDate = dateFormat.format(reservation.getDate());
        reservationDTO.setDate(formattedDate);

        List<String> emails = new ArrayList<String>();
        for (User friend : reservation.getInvited_friends()) {
            emails.add(friend.getEmail());
        }
        reservationDTO.setFriends(emails);

        reservationDTO.setId(reservation.getId());
        reservationDTO.setIdReserver(reservation.getReserver().getId());
        reservationDTO.setIdProjection(reservation.getProjection().getId());

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
