package com.isa.projekcije.controller;

import com.isa.projekcije.model.Show;
import com.isa.projekcije.model.dto.ThemePropsDTO;
import com.isa.projekcije.model.fanzone.ThemeProps;
import com.isa.projekcije.service.ShowService;
import com.isa.projekcije.service.ThemePropsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/themeprops")
public class ThemePropsController {

    @Autowired
    private ThemePropsService service;

    @Autowired
    private ShowService showService;

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

    /**
     * Creating new theme props based on dto.
     */
    @RequestMapping(value = "add", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addNewThemeProps(@RequestBody ThemePropsDTO themePropsDTO) {
        System.out.println(themePropsDTO.toString());
        try {
            Show show = showService.findOne(themePropsDTO.getShowId());
            ThemeProps themeProps = themePropsDTO.createThemeProps(show);
            themeProps = service.create(themeProps);
            return new ResponseEntity(themeProps, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity modifyThemeProps(ThemePropsDTO themePropsDTO) {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteThemeProps(@PathVariable long id) {
        try {
            ThemeProps themeProps = service.findById(id);
            service.delete(themeProps);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }
}
