package com.isa.projekcije.service;

import com.isa.projekcije.ProjekcijeApplicationTests;
import com.isa.projekcije.constants.InviteConstants;
import com.isa.projekcije.model.Invite;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class InviteServiceTest extends ProjekcijeApplicationTests {
    @Autowired
    InviteService inviteService;

    @Test
    public void findByReservationIdShouldReturnEmpty() {

        List<Invite> invites = inviteService.findByIdReservation(InviteConstants.NON_EXISTING_INVITE);
        assertThat(invites).isEmpty();
    }

    @Test
    public void findByReservationIdShouldReturnInvites() {

        List<Invite> invites = inviteService.findByIdReservation(InviteConstants.EXISTING_INVITE);
        assertThat(invites).isNotEmpty();
        assertThat(invites).hasSize(1);
    }


    @Test
    public void findByInvitedGuestIdShouldReturnEmpty() {

        List<Invite> invites = inviteService.findByIdInvitedUser(InviteConstants.USER_WITH_NON_EXISTING_INVITE);
        assertThat(invites).isEmpty();
    }

    @Test
    public void findByInvitedGuestIdShouldReturnInvites() {

        List<Invite> invites = inviteService.findByIdInvitedUser(InviteConstants.USER_WITH_EXISTING_INVITE);
        assertThat(invites).isNotEmpty();
        assertThat(invites).hasSize(1);
    }


    @Test
    public void findByIdReservationAndIdInvitedUserShouldReturnInvite() {

        Invite invite = inviteService.findByIdReservationAndIdInvitedUser(InviteConstants.EXISTING_INVITE, InviteConstants.USER_WITH_EXISTING_INVITE);
        assertThat(invite).isNotNull();
        assertThat(invite.getIdReservation()).isEqualTo(InviteConstants.EXISTING_INVITE);
        assertThat(invite.getIdInvitedUser()).isEqualTo(InviteConstants.USER_WITH_EXISTING_INVITE);
    }

    @Test
    public void findByIdReservationAndIdInvitedUserShouldReturnNull() {

        Invite invite = inviteService.findByIdReservationAndIdInvitedUser(InviteConstants.NON_EXISTING_INVITE, InviteConstants.USER_WITH_EXISTING_INVITE);
        assertThat(invite).isNull();
    }

}
