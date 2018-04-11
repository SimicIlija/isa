package com.isa.projekcije.converters;

import com.isa.projekcije.model.Projection;
import com.isa.projekcije.model.Show;
import com.isa.projekcije.model.Ticket;
import com.isa.projekcije.model.dto.ProjectionDTO;
import com.isa.projekcije.service.AuditoriumService;
import com.isa.projekcije.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ProjectionDTOToProjection implements Converter<ProjectionDTO, Projection> {

    @Autowired
    private AuditoriumService auditoriumService;

    @Autowired
    private ShowService showService;

    @Override
    public Projection convert(ProjectionDTO projectionDTO) {
        if (projectionDTO == null) {
            return null;
        }

        Projection projection = new Projection();

        if (projectionDTO.getId_auditorium() != null) {
            projection.setAuditorium(auditoriumService.findOne(projectionDTO.getId_auditorium()));
        }

        if (projectionDTO.getId_show() != null) {
            projection.setShow(showService.findOne(projectionDTO.getId_show()));
        }

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");
        Date date = null;
        try {
            date = formatter.parse(projectionDTO.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date);
        projection.setDate(date);

        projection.setTickets(new ArrayList<Ticket>());

        return projection;
    }

    public List<Projection> convert(List<ProjectionDTO> source) {

        List<Projection> projections = new ArrayList<Projection>();
        for (ProjectionDTO projectionDTO : source) {
            projections.add(convert(projectionDTO));
        }

        return projections;
    }


}
