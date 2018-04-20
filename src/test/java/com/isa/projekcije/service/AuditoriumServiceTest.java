package com.isa.projekcije.service;

import com.isa.projekcije.ProjekcijeApplicationTests;
import com.isa.projekcije.constants.AuditoriumConstants;
import com.isa.projekcije.model.Auditorium;
import com.isa.projekcije.model.Institution;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class AuditoriumServiceTest extends ProjekcijeApplicationTests {

    @Autowired
    private AuditoriumService auditoriumService;

    @Autowired
    private InstitutionService institutionService;

    @Test
    public void testFindAll() {
        List<Auditorium> institutions = auditoriumService.findAll();
        assertThat(institutions).hasSize(AuditoriumConstants.DB_COUNT_ALL);
    }

    @Test
    public void testFindOne() {
        Auditorium institution = auditoriumService.findOne(AuditoriumConstants.DB_ID);
        assertThat(institution).isNotNull();
        assertThat(institution.getId()).isEqualTo(AuditoriumConstants.DB_ID);
        assertThat(institution.getName()).isEqualTo(AuditoriumConstants.DB_NAME);
    }

    @Test
    public void testFindByInstitution() {
        List<Auditorium> auditoriums = auditoriumService.findByInstitution(AuditoriumConstants.DB_INSTITUTION_ID);
        assertThat(auditoriums).hasSize(AuditoriumConstants.DB_BY_INSTITUTION_COUNT);
        for (Auditorium auditorium : auditoriums) {
            assertThat(auditorium.getInstitution().getId()).isEqualTo(AuditoriumConstants.DB_INSTITUTION_ID);
        }
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testAdd() {
        Auditorium auditorium = new Auditorium();
        auditorium.setName(AuditoriumConstants.NEW_NAME);
        Institution institution = institutionService.findOne(AuditoriumConstants.DB_INSTITUTION_ID_TO_ADD);
        auditorium.setInstitution(institution);
        int dbSizeBeforeAdd = auditoriumService.findAll().size();

        Auditorium dbAuditorium = auditoriumService.save(auditorium);
        assertThat(dbAuditorium).isNotNull();

        List<Auditorium> auditoriums = auditoriumService.findAll();
        assertThat(auditoriums).hasSize(dbSizeBeforeAdd + 1);
        dbAuditorium = auditoriums.get(auditoriums.size() - 1);
        assertThat(dbAuditorium.getName()).isEqualTo(AuditoriumConstants.NEW_NAME);
        assertThat(dbAuditorium.getInstitution().getId()).isEqualTo(AuditoriumConstants.DB_INSTITUTION_ID_TO_ADD);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testEdit() {
        Auditorium dbAuditorium = auditoriumService.findOne(AuditoriumConstants.DB_ID_TO_EDIT);

        dbAuditorium.setName(AuditoriumConstants.NEW_NAME);

        dbAuditorium = auditoriumService.save(dbAuditorium);
        assertThat(dbAuditorium).isNotNull();

        dbAuditorium = auditoriumService.findOne(AuditoriumConstants.DB_ID_TO_EDIT);
        assertThat(dbAuditorium.getName()).isEqualTo(AuditoriumConstants.NEW_NAME);
    }

    @Test
    public void testDelete() {
        int dbSizeBeforeRemove = auditoriumService.findAll().size();

        auditoriumService.delete(AuditoriumConstants.DB_ID_TO_DELETE2);

        List<Auditorium> auditoriums = auditoriumService.findAll();
        assertThat(auditoriums).hasSize(dbSizeBeforeRemove - 1);

        Auditorium dbAuditorium = auditoriumService.findOne(AuditoriumConstants.DB_ID_TO_DELETE2);
        assertThat(dbAuditorium).isNull();
    }
}
