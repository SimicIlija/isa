package com.isa.projekcije.model.dto;

import com.isa.projekcije.model.Friendship;

public class FriendshipDTO {

    private Long id;
    private UserDTO sender;
    private UserDTO receiver;

    private boolean accepted;
    private boolean sent;

    public FriendshipDTO() {
    }

    public FriendshipDTO(Long id, UserDTO sender, UserDTO receiver, boolean accepted, boolean sent) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.accepted = accepted;
        this.sent = sent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getSender() {
        return sender;
    }

    public void setSender(UserDTO sender) {
        this.sender = sender;
    }

    public UserDTO getReceiver() {
        return receiver;
    }

    public void setReceiver(UserDTO receiver) {
        this.receiver = receiver;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }
}
