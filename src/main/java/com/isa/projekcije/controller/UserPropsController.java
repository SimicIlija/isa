package com.isa.projekcije.controller;

import com.isa.projekcije.model.User;
import com.isa.projekcije.model.dto.UserPropsDto;
import com.isa.projekcije.model.fanzone.UserProps;
import com.isa.projekcije.service.UserPropsService;
import com.isa.projekcije.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/userprops")
public class UserPropsController {
    @Autowired
    private UserPropsService userPropsService;

    @Autowired
    private UserService userService;

    /**
     * GET api/userprops
     * Returns all user props
     */
    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAll() {
        List<UserProps> result = userPropsService.findAll();
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * GET api/userprops/{id}
     * Returns UserProps with specific id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getSpecific(@PathVariable long id) {
        try {
            UserProps userProps = userPropsService.findById(id);
            return new ResponseEntity(userProps, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }

    /**
     * POST api/userprops/add
     * Create new UserProps by currently logged user @param userPropsDto
     */
    @RequestMapping(value = "add", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addNewUserProp(@Valid @RequestBody UserPropsDto userPropsDto) {
        try {
            User user = null; //TODO stavi pravog
            UserProps userProps = userPropsDto.createUserProps(user);
            userProps = userPropsService.create(userProps);
            return new ResponseEntity(userProps, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
