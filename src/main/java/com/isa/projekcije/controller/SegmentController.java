package com.isa.projekcije.controller;

import com.isa.projekcije.converters.SegmentDTOToSegmentConverter;
import com.isa.projekcije.converters.SegmentToSegmnetDTOConverter;
import com.isa.projekcije.model.*;
import com.isa.projekcije.model.dto.SegmentDTO;
import com.isa.projekcije.model.dto.SegmentTicketsDTO;
import com.isa.projekcije.service.ProjectionService;
import com.isa.projekcije.service.SeatService;
import com.isa.projekcije.service.SegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/segment")
public class SegmentController {

    @Autowired
    private SegmentService segmentService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private ProjectionService projectionService;

    @Autowired
    private SegmentDTOToSegmentConverter segmentDTOToSegmentConverter;

    @Autowired
    private SegmentToSegmnetDTOConverter segmentToSegmnetDTOConverter;

    @RequestMapping(
            value = "/getByAuditorium/{idAuditorium}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getByAuditorium(@PathVariable Long idAuditorium) {
        List<Segment> segmentList = segmentService.findByAuditorium(idAuditorium);
        return new ResponseEntity<>(segmentToSegmnetDTOConverter.convert(segmentList), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/getByProjection/{idProjection}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getByProjection(@PathVariable Long idProjection) {
        List<SegmentTicketsDTO> segments = new ArrayList<SegmentTicketsDTO>();
        Projection projection = projectionService.findById(idProjection);
        if (projection == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        for (Ticket ticket : projection.getTickets()) {
            boolean found = false;
            for (SegmentTicketsDTO segmentTicketsDTO : segments) {
                if (segmentTicketsDTO.getIdSegment().equals(ticket.getSeat().getSegment().getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                segments.add(new SegmentTicketsDTO(ticket.getSeat().getSegment().getId(), idProjection, ticket.getPrice().doubleValue()));
            }
        }
        return new ResponseEntity<>(segments, HttpStatus.OK);
    }


    @RequestMapping(
            value = "/getRest/{idProjection}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getRest(@PathVariable Long idProjection) {
        List<SegmentDTO> segments = new ArrayList<SegmentDTO>();
        Projection projection = projectionService.findById(idProjection);
        if (projection == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Auditorium auditorium = projection.getAuditorium();
        if (auditorium == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        for (Segment segment : auditorium.getSegments()) {
            boolean found = false;
            for (Ticket ticket : projection.getTickets()) {
                if (ticket.getSeat().getSegment().getId() == segment.getId()) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                segments.add(segmentToSegmnetDTOConverter.convert(segment));
            }
        }
        return new ResponseEntity<>(segments, HttpStatus.OK);
    }



    @RequestMapping(
            value = "/addSegment",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> addSegment(@RequestBody SegmentDTO segmentDTO) {
        Segment segment = segmentService.save(segmentDTOToSegmentConverter.convert(segmentDTO));
        for (int i = 0; i < segment.getRowCount(); i++) {
            for (int j = 0; j < segment.getSeatsInRowCount(); j++) {
                Seat seat = new Seat(i + 1, j + 1, segment);
                seatService.save(seat);
            }
        }
        return new ResponseEntity<>(segmentToSegmnetDTOConverter.convert(segment), HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/editSegment/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody SegmentDTO segmentDTO) {
        Segment edited = segmentService.findOne(id);
        if (edited == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        edited.setLabel(segmentDTO.getLabel());
        edited.setClosed(segmentDTO.isClosed());
        edited.setRowCount(segmentDTO.getRowCount());
        edited.setSeatsInRowCount(segmentDTO.getSeatsInRowCount());
        Segment newSegment = segmentService.save(edited);
        return new ResponseEntity<>(segmentToSegmnetDTOConverter.convert(newSegment), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/deleteSegment/{id}",
            method = RequestMethod.DELETE
    )
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Segment deleted = segmentService.delete(id);
        if (deleted == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(segmentToSegmnetDTOConverter.convert(deleted), HttpStatus.OK);
    }
}
