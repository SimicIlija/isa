package com.isa.projekcije.controller;

import com.isa.projekcije.converters.SeatDTOToSeat;
import com.isa.projekcije.converters.SeatToSeatDTO;
import com.isa.projekcije.model.Seat;
import com.isa.projekcije.model.User;
import com.isa.projekcije.model.dto.SeatDTO;
import com.isa.projekcije.service.SeatService;
import com.isa.projekcije.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/seats")
public class SeatController {
    @Autowired
    private SeatService seatService;

    @Autowired
    private SeatDTOToSeat seatDTOToSeat;

    @Autowired
    private SeatToSeatDTO seatToSeatDTO;

    @Autowired
    UserService userService;

    //fali deo za segmente
    @RequestMapping(
            value = "/{idSegment}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSeats(@PathVariable Long idSegment) {
        List<Seat> seats = new ArrayList<Seat>();
        List<SeatDTO> ticketsDTO = seatToSeatDTO.convert(seats);
        return new ResponseEntity(ticketsDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN_INST')")
    @RequestMapping(
            value = "/addSeat",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addSeat(@RequestBody SeatDTO seatToAdd) {

        Seat seat = seatDTOToSeat.convert(seatToAdd);
        Seat addedSeat = seatService.save(seat);
        SeatDTO seatDto = seatToSeatDTO.convert(addedSeat);

        return new ResponseEntity(seatDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN_INST')")
    @RequestMapping(
            value = "/{idSeatToDelete}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteSeat(@PathVariable Long idSeatToDelete) {
        Seat seat = seatService.delete(idSeatToDelete);
        SeatDTO seatDTO = seatToSeatDTO.convert(seat);
        return new ResponseEntity(seatDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN_INST')")
    @RequestMapping(
            value = "/{idSeat}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeSeat(@PathVariable Long idSeat, @RequestBody SeatDTO changedSeatDTO) {
        Seat seat = seatService.findById(idSeat);
        Seat changed = seatDTOToSeat.convert(changedSeatDTO);

        seat.setRow(changed.getRow());
        seat.setSeatNumber(changed.getSeatNumber());
        Seat saved = seatService.save(seat);
        SeatDTO seatDTO = seatToSeatDTO.convert(saved);

        return new ResponseEntity(seatDTO, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/reserveSeat/{idSeat}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeSeat(@PathVariable Long idSeat) {
        Seat seat = seatService.findById(idSeat);
        User reserver = userService.getCurrentUser();


        return new ResponseEntity(seat, HttpStatus.OK);
    }

}
