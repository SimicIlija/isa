package com.isa.projekcije.controller;

import com.isa.projekcije.model.fanzone.ThemeProps;
import com.isa.projekcije.service.ThemePropsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/themeprops")
public class ThemePropsController {

    @Autowired
    private ThemePropsService service;

    /**
     * Returns all theme props, for admins only
     */
    @RequestMapping(value = "all", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ThemeProps>> getAll() {
        List<ThemeProps> retVal = service.getAll();
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    /**
     * Returns only theme props available for shopping. For all users.
     */
    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ThemeProps>> getAvailable() {
        List<ThemeProps> retVal = service.getAvailable();
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }


}
