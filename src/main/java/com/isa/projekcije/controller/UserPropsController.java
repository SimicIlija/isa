package com.isa.projekcije.controller;

import com.isa.projekcije.model.User;
import com.isa.projekcije.model.dto.UserPropsDto;
import com.isa.projekcije.model.dto.UserPropsGetDto;
import com.isa.projekcije.model.fanzone.UserProps;
import com.isa.projekcije.model.fanzone.UserPropsState;
import com.isa.projekcije.service.UserPropsService;
import com.isa.projekcije.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/userprops")
public class UserPropsController {
    @Autowired
    private UserPropsService userPropsService;

    @Autowired
    private UserService userService;

    /**
     * GET api/userprops/unchecked
     * Returns unchecked user props for fz admin
     */
    @RequestMapping(value = "/unchecked", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAll() {
        List<UserProps> result = userPropsService.findUnchecked();
        List<UserPropsGetDto> retVal = result.stream()
                .map(UserPropsGetDto::createGetDtoFromUserProps).collect(Collectors.toList());
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    /**
     * GET api/userprops
     * Returns approved user props
     */
    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getApproved() {
        List<UserProps> result = userPropsService.findApproved();
        List<UserPropsGetDto> retVal = result.stream()
                .map(UserPropsGetDto::createGetDtoFromUserProps).collect(Collectors.toList());
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    /**
     * GET api/userprops/fza
     * Returns approved user props
     */
    @RequestMapping(value = "fza", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUnchecked() {
        List<UserProps> result = userPropsService.findApproved();
        return new ResponseEntity<>(result, HttpStatus.OK);
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

    /**
     * POST api/userprops/{id}
     * Approve or deny user props by fan zone admin
     * TODO : Check if admin of fun zone is sending request
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity acceptUserProps(@PathVariable long id, @RequestBody boolean approved) {
        try {
            UserProps userProps = userPropsService.findById(id);
            userProps.setState(approved ? UserPropsState.APPROVED : UserPropsState.DENIED);
            userProps = userPropsService.update(userProps);
            return new ResponseEntity(userProps, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


}
