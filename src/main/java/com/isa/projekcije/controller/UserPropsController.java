package com.isa.projekcije.controller;

import com.isa.projekcije.model.User;
import com.isa.projekcije.model.dto.UserPropsDto;
import com.isa.projekcije.model.fanzone.UserProps;
import com.isa.projekcije.model.fanzone.UserPropsState;
import com.isa.projekcije.service.StorageService;
import com.isa.projekcije.service.UserPropsService;
import com.isa.projekcije.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/userprops")
public class UserPropsController {
    @Autowired
    private UserPropsService userPropsService;

    @Autowired
    private UserService userService;

    @Autowired
    private StorageService storageService;

    /**
     * GET api/userprops/all
     * Returns all user props
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAll() {
        List<UserProps> result = userPropsService.findAll();
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * GET api/userprops
     * Returns approved user props
     */
    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getApproved() {
        List<UserProps> result = userPropsService.findApproved();
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

    /**
     * POST api/userprops/{id}/image
     * Upload image for user props with path variable id
     * TODO : Check if request is sent by user props creator
     */
    @RequestMapping(value = "/{id}/image", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity uploadFile(@PathVariable long id, @RequestParam("file") MultipartFile file) {
        try {
            UserProps userProps = userPropsService.findById(id);
            String imageUrl = storageService.store(file);
            userProps.setImageUrl(imageUrl);
            userPropsService.update(userProps);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET api/userprops/{id}/image
     * Download image for user props with path variable id
     */
    @RequestMapping(value = "/{id}/image", method = RequestMethod.GET)
    public ResponseEntity<Resource> getFile(@PathVariable long id) {
        try {
            UserProps userProps = userPropsService.findById(id);
            String imageUrl = userProps.getImageUrl();
            Resource resource = storageService.loadFile(imageUrl);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
