package com.isa.projekcije.controller;

import com.isa.projekcije.model.Friendship;
import com.isa.projekcije.model.User;
import com.isa.projekcije.service.FriendshipService;
import com.isa.projekcije.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friendships")
public class FriendshipController {

    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserController userController;

    @RequestMapping(
            value = "/create",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody User receiver) {
        User user = (User) userController.getRequest().getSession().getAttribute("user");
        // final Friendship friendship = friendshipService.create(sender, receiver.getId());
        final Friendship friendship = new Friendship();


        return new ResponseEntity<>(friendship, HttpStatus.CREATED);
    }

}
