package com.isa.projekcije.converters;

import com.isa.projekcije.model.Auditorium;
import com.isa.projekcije.model.Seat;
import com.isa.projekcije.model.Segment;
import com.isa.projekcije.model.dto.SegmentDTO;
import com.isa.projekcije.service.AuditoriumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SegmentDTOToSegmentConverter implements Converter<SegmentDTO, Segment> {

    @Autowired
    private AuditoriumService auditoriumService;

    @Override
    public Segment convert(SegmentDTO segmentDTO) {
        if (segmentDTO == null) {
            return null;
        }
        Segment segment = new Segment();
        segment.setLabel(segmentDTO.getLabel());
        segment.setClosed(segmentDTO.isClosed());
        segment.setSeats(new ArrayList<Seat>());
        segment.setRowCount(segmentDTO.getRowCount());
        segment.setSeatsInRowCount(segmentDTO.getSeatsInRowCount());
        if (segmentDTO.getIdAuditorium() != null) {
            Auditorium auditorium = auditoriumService.findOne(segmentDTO.getIdAuditorium());
            segment.setAuditorium(auditorium);
        }
        return segment;
    }
}
