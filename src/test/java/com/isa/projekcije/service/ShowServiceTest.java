package com.isa.projekcije.service;

import com.isa.projekcije.ProjekcijeApplicationTests;
import com.isa.projekcije.constants.ShowConstants;
import com.isa.projekcije.model.Show;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ShowServiceTest extends ProjekcijeApplicationTests {

    @Autowired
    private ShowService showService;

    @Test
    public void testFindAll() {
        List<Show> shows = showService.findAll();
        assertThat(shows).hasSize(ShowConstants.DB_COUNT);
    }

    @Test
    public void testFindOne() {
        Show show = showService.findOne(ShowConstants.DB_ID);
        assertThat(show).isNotNull();
        assertThat(show.getId()).isEqualTo(ShowConstants.DB_ID);
        assertThat(show.getName()).isEqualTo(ShowConstants.DB_NAME);
        assertThat(show.getGenre()).isEqualTo(ShowConstants.DB_GENRE);
        assertThat(show.getProducer()).isEqualTo(ShowConstants.DB_PRODUCER);
        assertThat(show.getDuration()).isEqualTo(ShowConstants.DB_DURATION);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testAdd() {
        Show show = new Show();
        show.setName(ShowConstants.NEW_NAME);
        show.setGenre(ShowConstants.NEW_GENRE);
        show.setProducer(ShowConstants.NEW_PRODUCER);
        show.setDuration(ShowConstants.NEW_DURATION);

        int dbSizeBeforeAdd = showService.findAll().size();

        Show dbShow = showService.save(show);
        assertThat(dbShow).isNotNull();

        List<Show> shows = showService.findAll();
        assertThat(shows).hasSize(dbSizeBeforeAdd + 1);
        dbShow = shows.get(shows.size() - 1);
        assertThat(dbShow.getName()).isEqualTo(ShowConstants.NEW_NAME);
        assertThat(dbShow.getGenre()).isEqualTo(ShowConstants.NEW_GENRE);
        assertThat(dbShow.getProducer()).isEqualTo(ShowConstants.NEW_PRODUCER);
        assertThat(dbShow.getDuration()).isEqualTo(ShowConstants.NEW_DURATION);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testEdit() {
        Show dbShow = showService.findOne(ShowConstants.DB_ID);

        dbShow.setName(ShowConstants.NEW_NAME);
        dbShow.setGenre(ShowConstants.NEW_GENRE);
        dbShow.setProducer(ShowConstants.NEW_PRODUCER);
        dbShow.setDuration(ShowConstants.NEW_DURATION);

        dbShow = showService.save(dbShow);
        assertThat(dbShow).isNotNull();

        dbShow = showService.findOne(ShowConstants.DB_ID);
        assertThat(dbShow.getName()).isEqualTo(ShowConstants.NEW_NAME);
        assertThat(dbShow.getGenre()).isEqualTo(ShowConstants.NEW_GENRE);
        assertThat(dbShow.getProducer()).isEqualTo(ShowConstants.NEW_PRODUCER);
        assertThat(dbShow.getDuration()).isEqualTo(ShowConstants.NEW_DURATION);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDelete() {
        int dbSizeBeforeRemove = showService.findAll().size();
        showService.delete(ShowConstants.DB_ID_TO_DELETE);

        /*List<Show> shows = showService.findAll();
        assertThat(shows).hasSize(dbSizeBeforeRemove - 1);*/

        Show dbShow = showService.findOne(ShowConstants.DB_ID_TO_DELETE);
        assertThat(dbShow).isNull();
    }
}
