package com.isa.projekcije.service;

import com.isa.projekcije.ProjekcijeApplicationTests;
import com.isa.projekcije.constants.ProjectionConstants;
import com.isa.projekcije.model.Projection;
import com.isa.projekcije.model.Reservation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ProjectionServiceTest extends ProjekcijeApplicationTests {

    @Autowired
    private ProjectionService projectionService;

    @Autowired
    private ShowService showService;

    @Autowired
    private AuditoriumService auditoriumService;

    @Test
    public void testFindOne() {
        Projection projection = projectionService.findOne(ProjectionConstants.DB_ID);
        assertThat(projection).isNotNull();
        assertThat(projection.getId()).isEqualTo(ProjectionConstants.DB_ID);
        assertThat(projection.getShow().getId()).isEqualTo(ProjectionConstants.DB_ID_SHOW);
        assertThat(projection.getAuditorium().getId()).isEqualTo(ProjectionConstants.DB_ID_AUDITORIUM);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testAdd() {
        Projection projection = new Projection();
        Reservation reservation = new Reservation();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = formatter.parse(ProjectionConstants.NEW_DATE);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        projection.setDate(date);
        projection.setAuditorium(auditoriumService.findOne(ProjectionConstants.DB_ID_AUDITORIUM));
        projection.setShow(showService.findOne(ProjectionConstants.DB_ID_SHOW));

        Projection dbProjection = projectionService.save(projection);
        assertThat(dbProjection).isNotNull();
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDelete() {
        projectionService.delete(ProjectionConstants.DB_ID_TO_DELETE);
        Projection projection = projectionService.findById(ProjectionConstants.DB_ID_TO_DELETE);
        assertThat(projection).isNull();
    }
}
