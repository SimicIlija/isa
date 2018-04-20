package com.isa.projekcije.service;

import com.isa.projekcije.ProjekcijeApplicationTests;
import com.isa.projekcije.constants.ActorConstants;
import com.isa.projekcije.model.Actor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ActorServiceTest extends ProjekcijeApplicationTests {

    @Autowired
    private ActorService actorService;

    @Autowired
    private ShowService showService;

    @Test
    public void testFindOne() {
        Actor actor = actorService.findById(ActorConstants.DB_ID);
        assertThat(actor).isNotNull();
        assertThat(actor.getId()).isEqualTo(ActorConstants.DB_ID);
        assertThat(actor.getName()).isEqualTo(ActorConstants.DB_NAME);
        assertThat(actor.getLastname()).isEqualTo(ActorConstants.DB_LASTNAME);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testAdd() {
        Actor actor = new Actor();
        actor.setName(ActorConstants.NEW_NAME);
        actor.setLastname(ActorConstants.NEW_LASTNAME);
        actor.setShow(showService.findOne(ActorConstants.DB_ID_SHOW));

        Actor dbActor = actorService.save(actor);
        assertThat(dbActor).isNotNull();
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDelete() {
        actorService.delete(ActorConstants.DB_ID);
        Actor actor = actorService.findById(ActorConstants.DB_ID);
        assertThat(actor).isNull();
    }
}
