package com.isa.projekcije.service;

import com.isa.projekcije.model.*;
import com.isa.projekcije.model.dto.LoginDTO;
import com.isa.projekcije.model.dto.RegistrationDTO;
import com.isa.projekcije.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
public class UserService {


    private UserRepository userRepository;


    public HttpServletRequest request;

    @Autowired
    public UserService(UserRepository userRepository, HttpServletRequest request) {
        this.userRepository = userRepository;
        this.request = request;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public User createUser(RegistrationDTO registrationDTO){
        User registeredUser = registrationDTO.createNewUser();
        System.out.println(registeredUser.getFirstName());
        return userRepository.save(registeredUser);
    }

    public boolean emailExists(RegistrationDTO registrationDTO){
        if (userRepository.findByEmail(registrationDTO.getEmail()) != null) {
            return true;
        }
        return false;
    }

    public User findUser(LoginDTO loginDTO){
        System.out.println(userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword()));
        return userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
    }

    public List<User> getAllUsers() {

        List<User> users = userRepository.findAll(new Sort("email"));
        /*User user = (User) request.getSession().getAttribute("user");
            for(User u : users){
                if(u.getEmail().equals(user.getEmail())){
                    users.remove(u);
                    break;
                }
            }*/
        return users;

    }

    public User getUserById(Long id) {
        LOGGER.debug("Getting user={}", id);
        return userRepository.findOne(id);
    }


    public User getUserByEmail(String email) {
        LOGGER.debug("Getting user by email={}", email);
        return userRepository.findByEmail(email);
    }

    public User createInstitutionAdmin(RegistrationDTO registrationDTO) {
        InstitutionAdmin institutionAdmin = registrationDTO.createNewInstitutionAdmin();
        System.out.println(institutionAdmin.getFirstName());
        return userRepository.save(institutionAdmin);
    }

    public void setCurrentUser(User user) {
        request.getSession().setAttribute("user", user);
        /* Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
         Authentication authentication = new PreAuthenticatedAuthenticationToken(user.getId(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);*/

    }

    public User getCurrentUser() {
        return (User) request.getSession().getAttribute("user");
         /*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());
        if(auth.getName().equals("anonymousUser")){
            return null;
        }
        try {
            Long id = Long.parseLong(auth.getName());
            return userRepository.findById(id);
        } catch (Exception e) {
           return null;
        }*/
    }


    public HttpServletRequest getRequest() {
        return null;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }


}
