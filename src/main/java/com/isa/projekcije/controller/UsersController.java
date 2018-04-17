package com.isa.projekcije.controller;

import com.isa.projekcije.model.User;
import com.isa.projekcije.model.dto.RegistrationDTO;
import com.isa.projekcije.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UsersController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UserService userService;


    @RequestMapping("/getAllUsers")
    public ResponseEntity<?> getUsersPage() {
        LOGGER.debug("Users page triggered");
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }




    @RequestMapping(
            value = "/registerInstitutionAdmin",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerInstitutionAdmin(@RequestBody RegistrationDTO registrationDTO) {
        System.out.println(registrationDTO.getFirstName());
        if (registrationDTO.isEmpty()) {
            return new ResponseEntity(new RuntimeException("Missing mandatory fields!"), HttpStatus.BAD_REQUEST);
        }

        if (!(registrationDTO.correctPassword())) {
            return new ResponseEntity(new RuntimeException("Passwords do not match!"), HttpStatus.BAD_REQUEST);
        }

        if (userService.emailExists(registrationDTO)) {
            return new ResponseEntity(new RuntimeException("Email already exists!"), HttpStatus.BAD_REQUEST);
        }
        User registeredUser = userService.createInstitutionAdmin(registrationDTO);
        System.out.println(registeredUser.getFirstName());
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);

    }
}
