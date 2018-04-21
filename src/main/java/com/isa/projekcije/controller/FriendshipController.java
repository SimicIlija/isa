package com.isa.projekcije.controller;

import com.isa.projekcije.converters.FriendshipToFriendshipDTO;
import com.isa.projekcije.converters.UserToUserDTO;
import com.isa.projekcije.model.Friendship;
import com.isa.projekcije.model.User;
import com.isa.projekcije.model.dto.FriendshipDTO;
import com.isa.projekcije.model.dto.UserDTO;
import com.isa.projekcije.service.FriendshipService;
import com.isa.projekcije.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Fidelity;
import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/friendships")
public class FriendshipController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private FriendshipService friendshipService;
    @Autowired
    private FriendshipToFriendshipDTO friendshipToFriendshipDTO;

    @Autowired
    private UserToUserDTO userToUserDTO;

    @Autowired
    private UserService userService;

    @RequestMapping("/getAllUsersExceptLoggedIn")
    public ResponseEntity<?> getAllUsersExceptLoggedIn() {
        LOGGER.debug("Users page triggered");
        return new ResponseEntity<>(friendshipService.getAllUsersExceptLoggedIn(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
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

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/removeFriend/{idRemove}",
            method = RequestMethod.DELETE)
    public ResponseEntity removeFriend(@PathVariable Long idRemove) {
        User sender = (User) userService.getCurrentUser();
        User receiver = userService.getUserById(idRemove);

        Friendship toDelete = friendshipService.findBySenderIdAndReceiverId(receiver.getId(), sender.getId());
        Friendship toDelete1 = friendshipService.findBySenderIdAndReceiverId(sender.getId(), receiver.getId());

        if (toDelete != null) {
            friendshipService.delete(toDelete.getId());
        }
        if (toDelete1 != null) {
            friendshipService.delete(toDelete1.getId());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/accept/{senderId}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity accept(@PathVariable long senderId) {
        final User receiver = (User) userService.getCurrentUser();
        final Friendship updatedFriendship = friendshipService.accept(senderId, receiver.getId());
        FriendshipDTO updatedFriendshipDTO = friendshipToFriendshipDTO.convert(updatedFriendship);
        return new ResponseEntity<>(updatedFriendshipDTO, HttpStatus.OK);
    }


    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/getAllFriendships",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getAllFriendships() {
        List<FriendshipDTO> friendships = friendshipService.getAllFriendships();

        return new ResponseEntity<>(friendships, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/getFriends",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getFriends() {
        User loggedIn = userService.getCurrentUser();

        List<Friendship> friendships = friendshipService.findBySenderIdOrReceiverId(loggedIn.getId(), loggedIn.getId());
        List<User> friends = new ArrayList<User>();
        for (Friendship f : friendships) {
            if (f.getSender().getId() == loggedIn.getId()) {
                friends.add(f.getReceiver());
            }
            if (f.getReceiver().getId() == loggedIn.getId()) {
                friends.add(f.getSender());
            }
        }
        List<UserDTO> friendsDTO = userToUserDTO.convert(friends);

        return new ResponseEntity<>(friendsDTO, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/getRequests",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getRequests() {
        List<FriendshipDTO> friendships = friendshipService.getAllRequests();

        return new ResponseEntity<>(friendships, HttpStatus.OK);
    }


    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/getConfirmed",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getConfirmed() {
        List<Friendship> friendships = friendshipService.findBySenderIdOrReceiverId(userService.getCurrentUser().getId(), userService.getCurrentUser().getId());
        List<Friendship> toRemove = new ArrayList<Friendship>();
        for (Friendship f : friendships) {
            if (!(f.isAccepted())) {
                toRemove.add(f);
            }
        }
        friendships.removeAll(toRemove);
        List<User> users = new ArrayList<User>();
        for (Friendship f : friendships) {
            if (f.getReceiver().getEmail().equals(userService.getCurrentUser().getEmail())) {
                users.add(f.getSender());
            }
            if (f.getSender().getEmail().equals(userService.getCurrentUser().getEmail())) {
                if (f.getReceiver().equals(userService.getCurrentUser().getEmail())) {
                    continue;
                } else {
                    users.add(f.getReceiver());
                }
            }

        }
        List<UserDTO> userDTOS = userToUserDTO.convert(users);

        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/searchByFirstName",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> searchByName(@RequestBody UserDTO userSearchByName) {
        List<Friendship> currentUserFriendships = friendshipService.findBySenderIdOrReceiverId(userService.getCurrentUser().getId(), userService.getCurrentUser().getId());
        List<Friendship> currentUserConfirmedFriendships = new ArrayList<Friendship>();
        for (Friendship f : currentUserFriendships) {
            if (f.isAccepted()) {
                currentUserConfirmedFriendships.add(f);
            }
        }
        List<User> retUser = new ArrayList<User>();
        List<User> userName = new ArrayList<User>();
        for (Friendship f : currentUserConfirmedFriendships) {
            if (!(f.getReceiver().getId() == userService.getCurrentUser().getId())) {
                retUser.add(f.getReceiver());
            }
            if (!(f.getSender().getId() == userService.getCurrentUser().getId())) {
                retUser.add(f.getSender());
            }


            for (User user : retUser) {
                if (userSearchByName != null) {
                    if (user.getFirstName().contains(userSearchByName.getFirstName())) {
                        userName.add(user);
                    }
                }
            }
        }
        List<UserDTO> usersDTO = userToUserDTO.convert(userName);

        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/searchByLastName",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> searchBySurname(@RequestBody UserDTO userSearchBySurname) {
        List<Friendship> currentUserFriendships = friendshipService.findBySenderIdOrReceiverId(userService.getCurrentUser().getId(), userService.getCurrentUser().getId());
        List<Friendship> currentUserConfirmedFriendships = new ArrayList<Friendship>();
        for (Friendship f : currentUserFriendships) {
            if (f.isAccepted()) {
                currentUserConfirmedFriendships.add(f);
            }
        }
        List<User> retUser = new ArrayList<User>();
        List<User> userName = new ArrayList<User>();
        for (Friendship f : currentUserConfirmedFriendships) {
            if (!(f.getReceiver().getId() == userService.getCurrentUser().getId())) {
                retUser.add(f.getReceiver());
            }
            if (!(f.getSender().getId() == userService.getCurrentUser().getId())) {
                retUser.add(f.getSender());
            }


            for (User user : retUser) {
                if (userSearchBySurname != null) {
                    if (user.getLastName().contains(userSearchBySurname.getLastName())) {
                        userName.add(user);
                    }
                }
            }
        }
        List<UserDTO> usersDTO = userToUserDTO.convert(userName);

        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }


    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/searchCombined",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> searchCombined(@RequestBody UserDTO userSearchBySurname) {
        List<Friendship> currentUserFriendships = friendshipService.findBySenderIdOrReceiverId(userService.getCurrentUser().getId(), userService.getCurrentUser().getId());
        List<Friendship> currentUserConfirmedFriendships = new ArrayList<Friendship>();
        for (Friendship f : currentUserFriendships) {
            if (f.isAccepted()) {
                currentUserConfirmedFriendships.add(f);
            }
        }
        List<User> retUser = new ArrayList<User>();
        List<User> userName = new ArrayList<User>();
        for (Friendship f : currentUserConfirmedFriendships) {
            if (!(f.getReceiver().getId() == userService.getCurrentUser().getId())) {
                retUser.add(f.getReceiver());
            }
            if (!(f.getSender().getId() == userService.getCurrentUser().getId())) {
                retUser.add(f.getSender());
            }


            for (User user : retUser) {
                if (userSearchBySurname != null) {
                    if (user.getLastName().contains(userSearchBySurname.getLastName()) && user.getFirstName().contains(userSearchBySurname.getFirstName())) {
                        userName.add(user);
                    }
                }
            }
        }
        List<UserDTO> usersDTO = userToUserDTO.convert(userName);

        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }

}
