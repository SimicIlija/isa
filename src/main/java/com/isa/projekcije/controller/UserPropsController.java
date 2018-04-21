package com.isa.projekcije.controller;

import com.isa.projekcije.model.User;
import com.isa.projekcije.model.dto.UserPropsDto;
import com.isa.projekcije.model.dto.UserPropsGetDto;
import com.isa.projekcije.model.fanzone.UserProps;
import com.isa.projekcije.model.fanzone.UserPropsState;
import com.isa.projekcije.service.BidService;
import com.isa.projekcije.service.UserPropsService;
import com.isa.projekcije.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    private BidService bidService;

    /**
     * GET api/userprops/unchecked
     * Returns unchecked user props for fz admin
     */
    @PreAuthorize("hasAuthority('ADMIN_FUN')")
    @RequestMapping(value = "/unchecked", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUnchecked() {
        List<UserProps> result = userPropsService.findUnchecked();
        List<UserPropsGetDto> retVal = result.stream()
                .map(UserPropsGetDto::createGetDtoFromUserProps).collect(Collectors.toList());
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    /**
     * GET api/userprops/mine
     * Returns user props created by user
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/mine", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getMyUserProps() {
        try {
            User user = userService.getCurrentUser();
            List<UserProps> myProps = userPropsService.findByUser(user.getId());
            List<UserPropsGetDto> retVal = myProps.stream()
                    .map(UserPropsGetDto::createGetDtoFromUserProps).collect(Collectors.toList());
            return new ResponseEntity<>(retVal, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    /**
     * GET api/userprops
     * Returns approved user props for bidding by user.
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getApproved() {
        User user = userService.getCurrentUser();
        List<UserProps> result = userPropsService.findApproved();
        List<UserProps> notUsers = result.stream()
                .filter(up -> !up.getCreator().getId().equals(user.getId())).collect(Collectors.toList());
        List<UserProps> notAccepted = notUsers.stream()
                .filter(up -> !bidService.isAccepted(up.getId())).collect(Collectors.toList());
        List<UserPropsGetDto> retVal = notAccepted.stream()
                .map(UserPropsGetDto::createGetDtoFromUserProps).collect(Collectors.toList());
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    /**
     * POST api/userprops/add
     * Create new UserProps by currently logged user @param userPropsDto
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "add", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addNewUserProp(@Valid @RequestBody UserPropsDto userPropsDto) {
        try {
            User user = userService.getCurrentUser();
            UserProps userProps = userPropsDto.createUserProps(user);
            userProps = userPropsService.create(userProps);
            return new ResponseEntity<>(UserPropsGetDto.createGetDtoFromUserProps(userProps), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * POST api/userprops/{id}
     * Approve or deny user props by fan zone admin
     */
    @PreAuthorize("hasAuthority('ADMIN_FUN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity acceptUserProps(@PathVariable long id, @RequestBody boolean approved) {
        try {
            UserProps userProps = userPropsService.findById(id);
            if (!userProps.isDateOk()) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            userProps.setState(approved ? UserPropsState.APPROVED : UserPropsState.DENIED);
            userProps = userPropsService.update(userProps);
            return new ResponseEntity<>(UserPropsGetDto.createGetDtoFromUserProps(userProps), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


}
