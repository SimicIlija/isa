package com.isa.projekcije.converters;

import com.isa.projekcije.model.Segment;
import com.isa.projekcije.model.dto.SegmentDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SegmentToSegmnetDTOConverter implements Converter<Segment, SegmentDTO> {

    @Override
    public SegmentDTO convert(Segment segment) {
        if (segment == null) {
            return null;
        }
        return new SegmentDTO(segment.getId(), segment.getLabel(), segment.isClosed(), segment.getAuditorium().getId());
    }

    public List<SegmentDTO> convert(List<Segment> segmentList) {
        List<SegmentDTO> segmentDTOList = new ArrayList<SegmentDTO>();
        for (Segment segment : segmentList) {
            segmentDTOList.add(convert(segment));
        }
        return segmentDTOList;
    }
}
