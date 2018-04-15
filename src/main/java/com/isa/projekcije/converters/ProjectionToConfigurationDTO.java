package com.isa.projekcije.converters;

import com.isa.projekcije.model.*;
import com.isa.projekcije.model.dto.ConfigurationDTO;
import com.isa.projekcije.model.dto.SeatTicketDTO;
import com.isa.projekcije.model.dto.SegmentConfigDTO;
import com.isa.projekcije.repository.AuditoriumRepository;
import com.isa.projekcije.repository.ProjectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProjectionToConfigurationDTO implements Converter<Projection, ConfigurationDTO> {

    @Autowired
    private ProjectionRepository projectionRepository;

    @Override
    public ConfigurationDTO convert(Projection projection) {
        if (projection == null) {
            return null;
        }
        ConfigurationDTO configurationDTO = new ConfigurationDTO();
        Auditorium aud = projection.getAuditorium();
        configurationDTO.setIdAuditorium(aud.getId());
        configurationDTO.setNameAuditorium(aud.getName());

        for (Segment segment : projection.getAuditorium().getSegments()) {
            SegmentConfigDTO segmentConfigDTO = new SegmentConfigDTO();
            segmentConfigDTO.setId(segment.getId());
            segmentConfigDTO.setLabel(segment.getLabel());
            segmentConfigDTO.setRowCount(segment.getRowCount());
            segmentConfigDTO.setSeatsInRowCount(segment.getSeatsInRowCount());
            for (Seat seat : segment.getSeats()) {
                for (Ticket t : seat.getTickets()) {

                    if (t.getProjection().getId() == (projection.getId())) {
                        SeatTicketDTO seatTicketDTO = new SeatTicketDTO();
                        seatTicketDTO.setPrice(t.getPrice());
                        seatTicketDTO.setReserved(t.isReserved());
                        seatTicketDTO.setRow(seat.getRow());
                        seatTicketDTO.setIdTicket(t.getId());
                        seatTicketDTO.setSeatNumber(seat.getSeatNumber());
                        segmentConfigDTO.getSeatTicketDTOList().add(seatTicketDTO);
                    }
                }
            }
            configurationDTO.getSegments().add(segmentConfigDTO);
        }


        return configurationDTO;
    }
}
