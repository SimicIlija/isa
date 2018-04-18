package com.isa.projekcije.service;

import com.isa.projekcije.ProjekcijeApplicationTests;
import com.isa.projekcije.constants.InstitutionConstants;
import com.isa.projekcije.model.Institution;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class InstitutionServiceTest extends ProjekcijeApplicationTests {

    @Autowired
    InstitutionService institutionService;

    @Test
    public void testFindAll() {
        List<Institution> institutions = institutionService.findAll();
        assertThat(institutions).hasSize(InstitutionConstants.DB_COUNT);
    }

    @Test
    public void testFindOne() {
        Institution institution = institutionService.findOne(InstitutionConstants.DB_ID);
        assertThat(institution).isNotNull();
        assertThat(institution.getId()).isEqualTo(InstitutionConstants.DB_ID);
        assertThat(institution.getName()).isEqualTo(InstitutionConstants.DB_NAME);
        assertThat(institution.getLongitude()).isEqualTo(InstitutionConstants.DB_LONGITUDE);
        assertThat(institution.getLatitude()).isEqualTo(InstitutionConstants.DB_LATITUDE);
        assertThat(institution.isCinema()).isEqualTo(InstitutionConstants.DB_ISCINEMA);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testAdd() {
        Institution institution = new Institution();
        institution.setName(InstitutionConstants.NEW_NAME);
        institution.setDescription(InstitutionConstants.NEW_DESCRIPTION);
        institution.setLongitude(InstitutionConstants.NEW_LONGITUDE);
        institution.setLatitude(InstitutionConstants.NEW_LATITUDE);
        institution.setCinema(InstitutionConstants.NEW_ISCINEMA);

        int dbSizeBeforeAdd = institutionService.findAll().size();

        Institution dbInstitution = institutionService.save(institution);
        assertThat(dbInstitution).isNotNull();

        List<Institution> institutionList = institutionService.findAll();
        assertThat(institutionList).hasSize(dbSizeBeforeAdd + 1);
        dbInstitution = institutionList.get(institutionList.size() - 1);
        assertThat(dbInstitution.getName()).isEqualTo(InstitutionConstants.NEW_NAME);
        assertThat(dbInstitution.getDescription()).isEqualTo(InstitutionConstants.NEW_DESCRIPTION);
        assertThat(dbInstitution.getLongitude()).isEqualTo(InstitutionConstants.NEW_LONGITUDE);
        assertThat(dbInstitution.getLatitude()).isEqualTo(InstitutionConstants.NEW_LATITUDE);
        assertThat(dbInstitution.isCinema()).isEqualTo(InstitutionConstants.NEW_ISCINEMA);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testEdit() {
        Institution dbInstitution = institutionService.findOne(InstitutionConstants.DB_ID);

        dbInstitution.setName(InstitutionConstants.NEW_NAME);
        dbInstitution.setDescription(InstitutionConstants.NEW_DESCRIPTION);
        dbInstitution.setLongitude(InstitutionConstants.NEW_LONGITUDE);
        dbInstitution.setLatitude(InstitutionConstants.NEW_LATITUDE);
        dbInstitution.setCinema(InstitutionConstants.NEW_ISCINEMA);

        dbInstitution = institutionService.save(dbInstitution);
        assertThat(dbInstitution).isNotNull();

        dbInstitution = institutionService.findOne(InstitutionConstants.DB_ID);
        assertThat(dbInstitution.getName()).isEqualTo(InstitutionConstants.NEW_NAME);
        assertThat(dbInstitution.getDescription()).isEqualTo(InstitutionConstants.NEW_DESCRIPTION);
        assertThat(dbInstitution.getLongitude()).isEqualTo(InstitutionConstants.NEW_LONGITUDE);
        assertThat(dbInstitution.getLatitude()).isEqualTo(InstitutionConstants.NEW_LATITUDE);
        assertThat(dbInstitution.isCinema()).isEqualTo(InstitutionConstants.NEW_ISCINEMA);
    }

    /*@Test
    @Transactional
    @Rollback(true)
    public void testDelete() {
        int dbSizeBeforeRemove = institutionService.findAll().size();
        institutionService.delete(InstitutionConstants.DB_ID_TO_DELETE);

        List<Institution> institutions = institutionService.findAll();
        assertThat(institutions).hasSize(dbSizeBeforeRemove - 1);

        Institution dbInstitution = institutionService.findOne(InstitutionConstants.DB_ID_TO_DELETE);
        assertThat(dbInstitution).isNull();
    }*/

}
