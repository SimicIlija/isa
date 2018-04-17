package com.isa.projekcije.converters;

import com.isa.projekcije.model.Projection;
import com.isa.projekcije.model.dto.ProjectionDTO;
import com.isa.projekcije.repository.ProjectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectionToProjectionDTO implements Converter<Projection, ProjectionDTO> {

    @Autowired
    private ProjectionRepository projectionRepository;

    @Override
    public ProjectionDTO convert(Projection projection) {
        if (projection == null) {
            return null;
        }

        ProjectionDTO projectionDTO = new ProjectionDTO();

        projectionDTO.setId(projection.getId());

        if (projection.getAuditorium() != null) {
            projectionDTO.setId_auditorium(projection.getAuditorium().getId());
        }
        if (projection.getShow() != null) {
            projectionDTO.setId_show(projection.getShow().getId());
        }
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        projectionDTO.setDate(formatter.format(projection.getDate()));


        return projectionDTO;
    }

    public List<ProjectionDTO> convert(List<Projection> source) {

        List<ProjectionDTO> projectionDTO = new ArrayList<ProjectionDTO>();
        for (Projection projection : source) {
            projectionDTO.add(convert(projection));
        }

        return projectionDTO;
    }
}
