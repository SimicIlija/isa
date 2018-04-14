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

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public User createUser(RegistrationDTO registrationDTO){
        RegisteredUser registeredUser = registrationDTO.createNewUser();
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
        LOGGER.debug("Getting all users");
        return userRepository.findAll(new Sort("email"));
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

        final Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        final Authentication authentication = new PreAuthenticatedAuthenticationToken(user.getId(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    public User getCurrentUser() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            return userRepository.findById((Long) auth.getPrincipal());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
