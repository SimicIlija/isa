package com.isa.projekcije.controller;

import com.isa.projekcije.model.dto.ThemePropsDTO;
import com.isa.projekcije.model.dto.ThemePropsGetDto;
import com.isa.projekcije.model.fanzone.ThemeProps;
import com.isa.projekcije.service.ShowService;
import com.isa.projekcije.service.StorageService;
import com.isa.projekcije.service.ThemePropsService;
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
@RequestMapping(value = "api/themeprops")
public class ThemePropsController {

    @Autowired
    private ThemePropsService themePropsService;

    @Autowired
    private ShowService showService;

    @Autowired
    private StorageService storageService;

    /**
     * GET api/themeprops/all
     * Returns all theme props, for admins only
     */
    @PreAuthorize("hasAuthority('ADMIN_FUN')")
    @RequestMapping(value = "all", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAll() {
        List<ThemeProps> list = themePropsService.getAll();
        List<ThemePropsGetDto> retVal = list.stream().map(ThemePropsGetDto::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    /**
     * GET api/themeprops
     * Returns only theme props available for shopping. For all users.
     */
    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAvailable() {
        List<ThemeProps> list = themePropsService.getAvailable();
        List<ThemePropsGetDto> retVal = list.stream().map(ThemePropsGetDto::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    /**
     * POST api/themeprops/add
     * Creating new theme props based on dto.
     */
    @PreAuthorize("hasAuthority('ADMIN_FUN')")
    @RequestMapping(value = "add", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addNewThemeProps(@Valid @RequestBody ThemePropsDTO themePropsDTO) {
        try {
            ThemeProps themeProps = themePropsDTO.createThemeProps();
            themeProps = themePropsService.create(themeProps);
            ThemePropsGetDto retVal = new ThemePropsGetDto(themeProps);
            return new ResponseEntity<>(retVal, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * PUT api/themeprops/{id}
     * Update theme props with path variable id
     */
    @PreAuthorize("hasAuthority('ADMIN_FUN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity modifyThemeProps(@Valid @RequestBody ThemePropsDTO themePropsDTO, @PathVariable long id) {
        try {
            System.out.println(themePropsDTO);
            ThemeProps themeProps = themePropsService.findById(id);
            themeProps.setPrice(themePropsDTO.getPrice());
            themeProps.setAmount(themePropsDTO.getAmount());
            themeProps.setName(themePropsDTO.getName());
            themeProps.setDescription(themePropsDTO.getDescription());
            themeProps.setImageUrl(themePropsDTO.getImageUrl());
            themePropsService.update(themeProps);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * DELETE api/themeprops/{id}
     * Delete theme props with path variable id
     */
    @PreAuthorize("hasAuthority('ADMIN_FUN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteThemeProps(@PathVariable long id) {
        System.out.println(id);
        try {
            ThemeProps themeProps = themePropsService.findById(id);
            themePropsService.delete(themeProps);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
