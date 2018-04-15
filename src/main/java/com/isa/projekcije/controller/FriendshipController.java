package com.isa.projekcije.controller;

import com.isa.projekcije.model.Friendship;
import com.isa.projekcije.model.User;
import com.isa.projekcije.service.FriendshipService;
import com.isa.projekcije.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/friendships")
public class FriendshipController {


    @Autowired
    private FriendshipService friendshipService;


    @Autowired
    private UserService userService;


    @RequestMapping(
            value = "/create/{idCreate}",
            method = RequestMethod.POST)
    public ResponseEntity create(@PathVariable Long idCreate) {
        User sender = (User) userService.getCurrentUser();
        User receiver = userService.getUserById(idCreate);
        final Friendship friendship = friendshipService.create(sender, receiver.getEmail());
        if (friendship == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/accept/{senderId}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity accept(@PathVariable long senderId) {
        final User receiver = (User) userService.getCurrentUser();
        final Friendship updatedFriendship = friendshipService.accept(senderId, receiver.getId());

        return new ResponseEntity<>(updatedFriendship, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/getFriendsNotAccepted",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> getFriendsNotAccepted() {
        List<Friendship> friendships = friendshipService.getFriendsNotAccepted();
        return new ResponseEntity<>(friendships, HttpStatus.OK);
    }
}
