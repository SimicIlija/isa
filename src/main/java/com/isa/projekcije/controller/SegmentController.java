package com.isa.projekcije.controller;

import com.isa.projekcije.converters.SegmentDTOToSegmentConverter;
import com.isa.projekcije.converters.SegmentToSegmnetDTOConverter;
import com.isa.projekcije.model.Segment;
import com.isa.projekcije.model.dto.SegmentDTO;
import com.isa.projekcije.service.SegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/segment")
public class SegmentController {

    @Autowired
    private SegmentService segmentService;

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
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> addSegment(@RequestBody SegmentDTO segmentDTO) {
        Segment segment = segmentService.save(segmentDTOToSegmentConverter.convert(segmentDTO));
        return new ResponseEntity<>(segmentToSegmnetDTOConverter.convert(segment), HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "{id}",
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
        Segment newSegment = segmentService.save(edited);
        return new ResponseEntity<>(segmentToSegmnetDTOConverter.convert(newSegment), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/{id}",
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
