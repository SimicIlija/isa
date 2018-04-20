package com.isa.projekcije.controller;


import com.isa.projekcije.converters.RegistrationDTOToUser;
import com.isa.projekcije.converters.UserToUserDTO;
import com.isa.projekcije.model.*;
import com.isa.projekcije.model.dto.*;
import com.isa.projekcije.model.fanzone.FanZoneAdmin;
import com.isa.projekcije.service.EmailService;
import com.isa.projekcije.service.ProjectionRatingService;
import com.isa.projekcije.service.RandomString;
import com.isa.projekcije.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@RestController
@RequestMapping(value = "/user")
public class UserController {


    @Autowired
    RegistrationDTOToUser registrationDTOToUser;

    @Autowired
    private UserService userService;

    @Autowired
    private UserToUserDTO userToUserDTO;

    @Autowired
    private ProjectionRatingService projectionRatingService;


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

            if (user.getRole() == Role.ADMIN_FUN) {
                FanZoneAdmin fanZoneAdmin = (FanZoneAdmin) userService.findById(user.getId());
                if (!fanZoneAdmin.isChangedPassword()) {
                    userService.setCurrentUser(user);
                    return new ResponseEntity<>(HttpStatus.MOVED_PERMANENTLY);
                }
            }

            if (user.getRole() == Role.ADMIN_INST) {
                InstitutionAdmin institutionAdmin = (InstitutionAdmin) userService.findById(user.getId());
                if (!institutionAdmin.isPasswordChanged()) {
                    userService.setCurrentUser(user);
                    return new ResponseEntity<>(HttpStatus.MOVED_PERMANENTLY);
                }
            }

            userService.setCurrentUser(user);
            UserDTO userDTO = userToUserDTO.convert(user);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(
            value = "/getUserByEmail",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserByEmail(@RequestBody UserDTO email) {
        User user = this.userService.findByEmail(email.getEmail());
        if (user != null) {
            UserDTO userDTO = userToUserDTO.convert(user);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(
            value = "/getUserById/{idUser}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getReserver(@PathVariable Long idUser) {
        User user = userService.findById(idUser);
        UserDTO userDTO = userToUserDTO.convert(user);
        return new ResponseEntity(userDTO, HttpStatus.OK);
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

            retRegisteredUser = userService.createUser(registrationDTO);


            emailService.getMail().setTo(retRegisteredUser.getEmail());
            emailService.getMail().setFrom(emailService.getEnv().getProperty("spring.mail.username"));
            emailService.getMail().setSubject("Setting password for your account");
            emailService.getMail().setText("Hello " + retRegisteredUser.getFirstName() + ",\n\nThis is your new password:\n\n" + retRegisteredUser.getPassword() + "");
            emailService.sendNotificaitionAsync(registeredUser);

            UserDTO retRegisteredUserDTO = userToUserDTO.convert(retRegisteredUser);
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

        UserDTO userDTO = new UserDTO();
        if (userService.getCurrentUser() != null) {
            userDTO = userToUserDTO.convert(userService.getCurrentUser());
            userDTO.setRole(userService.getCurrentUser().getRole().toString());
            return new ResponseEntity<>(userDTO, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/authRole", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity authRole() {
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            RoleDto roleDto = RoleDto.createRoleDto(currentUser);
            return new ResponseEntity<>(roleDto, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }
    /*@RequestMapping(
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
    }*/


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
            UserDTO dto = userToUserDTO.convert(u);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("isAuthenticated()")
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
            UserDTO dto = userToUserDTO.convert(u);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/logout",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity signout() {
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
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
            UserDTO dto = userToUserDTO.convert(u);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("isAuthenticated()")
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
            UserDTO dto = userToUserDTO.convert(u);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("isAuthenticated()")
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

    @PreAuthorize("isAuthenticated()")
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
        if (usrLoggedIn.getRole() == Role.ADMIN_FUN) {
            FanZoneAdmin fanZoneAdmin = (FanZoneAdmin) userService.findById(usrLoggedIn.getId());
            fanZoneAdmin.setChangedPassword(true);
            userService.save(fanZoneAdmin);
        } else if (usrLoggedIn.getRole() == Role.ADMIN_INST) {
            InstitutionAdmin institutionAdmin = (InstitutionAdmin) userService.findById(usrLoggedIn.getId());
            institutionAdmin.setPasswordChanged(true);
            userService.save(institutionAdmin);
        } else {
            userService.save(usrLoggedIn);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/getMyVisits",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> getMyProjections() {
        List<Long> projectionIds = new ArrayList<Long>();
        List<VisitDTO> visitDTOList = new ArrayList<VisitDTO>();
        User loggedIn = userService.getCurrentUser();
        if (loggedIn == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        for (Reservation reservation : loggedIn.getReservations()) {
            if (!projectionIds.contains(reservation.getProjection().getId())) {
                Date todayDate = new Date();
                if (todayDate.after(reservation.getProjection().getDate())) {
                    VisitDTO visitDTO = new VisitDTO(reservation.getProjection().getAuditorium().getInstitution().getId(),
                            reservation.getProjection().getAuditorium().getInstitution().getName(),
                            reservation.getProjection().getShow().getId(),
                            reservation.getProjection().getShow().getName(),
                            reservation.getProjection().getId(), loggedIn.getId());

                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    visitDTO.setDate(formatter.format(reservation.getProjection().getDate()));

                    ProjectionRating projectionRating = projectionRatingService.getByUserAndProjection(loggedIn.getId(),
                            reservation.getProjection().getId());
                    if (projectionRating != null) {
                        visitDTO.setMyProjectionRating(projectionRating.getProjectionRating());
                        visitDTO.setMyInstitutionRating(projectionRating.getInstitutionRating());
                        visitDTO.setIdRating(projectionRating.getId());
                    } else {
                        visitDTO.setMyProjectionRating(-1);
                        visitDTO.setIdRating(-1L);
                    }
                    visitDTOList.add(visitDTO);
                    projectionIds.add(reservation.getProjection().getId());
                }
            }
        }
        return new ResponseEntity<>(visitDTOList, HttpStatus.OK);
    }

}
