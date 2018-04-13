package com.isa.projekcije.service;

import com.isa.projekcije.ProjekcijeApplicationTests;
import com.isa.projekcije.constants.SegmentConstants;
import com.isa.projekcije.model.Auditorium;
import com.isa.projekcije.model.Segment;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class SegmentServiceTest extends ProjekcijeApplicationTests {

    @Autowired
    private SegmentService segmentService;

    @Autowired
    private AuditoriumService auditoriumService;

    @Test
    public void testFindAll() {
        List<Segment> segments = segmentService.findAll();
        assertThat(segments).hasSize(SegmentConstants.DB_COUNT);
    }

    @Test
    public void testFindOne() {
        Segment segment = segmentService.findOne(SegmentConstants.DB_ID);
        assertThat(segment).isNotNull();
        assertThat(segment.getId()).isEqualTo(SegmentConstants.DB_ID);
        assertThat(segment.getLabel()).isEqualTo(SegmentConstants.DB_LABEL);
        assertThat(segment.isClosed()).isEqualTo(SegmentConstants.DB_CLOSED);
    }

    @Test
    public void testFindByAuditorium() {
        List<Segment> segments = segmentService.findByAuditorium(SegmentConstants.DB_AUDITORIUM_ID);
        assertThat(segments).hasSize(SegmentConstants.DB_BY_AUDITORIUM_COUNT);
        for (Segment segment : segments) {
            assertThat(segment.getAuditorium().getId()).isEqualTo(SegmentConstants.DB_AUDITORIUM_ID);
        }
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testAdd() {
        Segment segment = new Segment();
        segment.setLabel(SegmentConstants.NEW_LABEL);
        segment.setClosed(SegmentConstants.NEW_CLOSED);
        Auditorium auditorium = auditoriumService.findOne(SegmentConstants.DB_AUDITORIUM_ID);
        segment.setAuditorium(auditorium);
        int dbSizeBeforeAdd = segmentService.findAll().size();

        Segment dbSegment = segmentService.save(segment);
        assertThat(dbSegment).isNotNull();

        List<Segment> segments = segmentService.findAll();
        assertThat(segments).hasSize(dbSizeBeforeAdd + 1);
        dbSegment = segments.get(segments.size() - 1);
        assertThat(dbSegment.getLabel()).isEqualTo(SegmentConstants.NEW_LABEL);
        assertThat(dbSegment.isClosed()).isEqualTo(SegmentConstants.NEW_CLOSED);
        assertThat(dbSegment.getAuditorium().getId()).isEqualTo(SegmentConstants.DB_AUDITORIUM_ID);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testEdit() {
        Segment dbSegment = segmentService.findOne(SegmentConstants.DB_ID);

        dbSegment.setLabel(SegmentConstants.NEW_LABEL);
        dbSegment.setClosed(SegmentConstants.NEW_CLOSED);

        dbSegment = segmentService.save(dbSegment);
        assertThat(dbSegment).isNotNull();

        dbSegment = segmentService.findOne(SegmentConstants.DB_ID);
        assertThat(dbSegment.getLabel()).isEqualTo(SegmentConstants.NEW_LABEL);
        assertThat(dbSegment.isClosed()).isEqualTo(SegmentConstants.NEW_CLOSED);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDelete() {
        int dbSizeBeforeRemove = segmentService.findAll().size();
        segmentService.delete(SegmentConstants.DB_ID_TO_DELETE);

        List<Segment> segments = segmentService.findAll();
        assertThat(segments).hasSize(dbSizeBeforeRemove - 1);

        Segment dbSegment = segmentService.findOne(SegmentConstants.DB_ID_TO_DELETE);
        assertThat(dbSegment).isNull();
    }
}
