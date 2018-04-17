package com.isa.projekcije.controller;


import com.isa.projekcije.converters.RegistrationDTOToUser;
import com.isa.projekcije.model.ServletContextImpl;

import com.isa.projekcije.model.User;
import com.isa.projekcije.model.dto.ChangePasswordDTO;
import com.isa.projekcije.model.dto.LoginDTO;
import com.isa.projekcije.model.dto.RegistrationDTO;

import com.isa.projekcije.service.UserService;

import com.isa.projekcije.service.EmailService;
import com.isa.projekcije.service.RandomString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.isa.projekcije.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ThreadLocalRandom;


@RestController
@RequestMapping(value = "/user")
public class UserController {



    @Autowired
    RegistrationDTOToUser registrationDTOToUser;

    @Autowired
    private UserService userService;


    @Autowired
    private EmailService emailService;

    @RequestMapping(
            value = "/login",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        User user = this.userService.findUser(loginDTO);

        if (user != null) {
            userService.setCurrentUser(user);

            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


    @RequestMapping(
            value = "/registerUser",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@RequestBody RegistrationDTO registrationDTO) {
        System.out.println(registrationDTO.getFirstName());
        if (registrationDTO.isEmpty()) {
            return new ResponseEntity(new RuntimeException("Missing mandatory fields!"), HttpStatus.BAD_REQUEST);
        }


        if (!(registrationDTO.correctPassword())) {
            return new ResponseEntity(new RuntimeException("Passwords do not match!"), HttpStatus.BAD_REQUEST);
        }

        /*if(!(registrationDTO.correctPassword())){
            return new ResponseEntity(new RuntimeException("Passwords do not match!"),HttpStatus.BAD_REQUEST);
        }*/
      if (userService.emailExists(registrationDTO)) {
            return new ResponseEntity(new RuntimeException("Email already exists!"), HttpStatus.BAD_REQUEST);
        }



        RandomString gen = new RandomString(10, ThreadLocalRandom.current());
        String newPassword = gen.nextString();
        registrationDTO.setPassword(newPassword);
        User registeredUser = registrationDTOToUser.convert(registrationDTO);
        User retRegisteredUser = null;
        try {

            emailService.sendNotificaitionAsync(registeredUser);

            retRegisteredUser = userService.createUser(registrationDTO);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        System.out.println(retRegisteredUser.getFirstName());
        return new ResponseEntity<>(retRegisteredUser, HttpStatus.CREATED);

    }

    @RequestMapping(
            value = "/getLoggedInUser",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLoggedInUser() {
        if (userService.getCurrentUser() != null) {
            return new ResponseEntity<>(userService.getCurrentUser(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(
            value = "/logout",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> logout() {
        Boolean success = userService.logout();
        if (success == true) {
            return new ResponseEntity<>(success, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(success, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(
            value = "/changeFirstName",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeFirstName(@RequestBody User user) {
        if (userService.getCurrentUser() != null) {
            User usr = userService.getCurrentUser();
            usr.setFirstName(user.getFirstName());
            User u = userService.save(usr);
            userService.setCurrentUser(u);
            return new ResponseEntity<>(u, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(
            value = "/changeLastName",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeLastName(@RequestBody User user) {
        if (userService.getCurrentUser() != null) {
            User usr = userService.getCurrentUser();
            usr.setLastName(user.getLastName());
            User u = userService.save(usr);
            userService.setCurrentUser(u);
            return new ResponseEntity<>(u, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/signout")
    public ResponseEntity signout() {
        SecurityContextHolder.clearContext();
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(
            value = "/changeEmail",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeEmail(@RequestBody User user) {
        if (userService.getCurrentUser() != null) {
            User usr = userService.getCurrentUser();

            for (User userNew : userService.getAllUsers()) {
                if (userNew.getEmail().equals(user.getEmail())) {
                    return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
                }
            }
            usr.setEmail(user.getEmail());
            User u = userService.save(usr);
            userService.setCurrentUser(u);
            return new ResponseEntity<>(u, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(
            value = "/changePhoneNumber",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePhoneNumber(@RequestBody User user) {
        if (userService.getCurrentUser() != null) {
            User usr = userService.getCurrentUser();
            usr.setPhoneNumber(user.getPhoneNumber());
            User u = userService.save(usr);
            userService.setCurrentUser(u);
            return new ResponseEntity<>(u, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(
            value = "/sendEmail",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendEmail(@RequestBody User user) {
        try {
            RandomString gen = new RandomString(10, ThreadLocalRandom.current());
            String newPassword = gen.nextString();

            User usr = userService.findByEmail(user.getEmail());
            if (usr == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            usr.setPassword(newPassword);

            emailService.sendNotificaitionAsync(usr);
            userService.save(usr);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(
            value = "/changePassword",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {


        if (!(changePasswordDTO.getPassword().equals(changePasswordDTO.getPasswordConfirm()))) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        User usrLoggedIn = userService.getCurrentUser();
        if (!(usrLoggedIn.getPassword().equals(changePasswordDTO.getCurrentPassword()))) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        usrLoggedIn.setPassword(changePasswordDTO.getPassword());
        userService.save(usrLoggedIn);

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
