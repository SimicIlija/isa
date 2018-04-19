package com.isa.projekcije.service;

import com.isa.projekcije.model.InstitutionAdmin;
import com.isa.projekcije.model.SystemAdmin;
import com.isa.projekcije.model.User;
import com.isa.projekcije.model.dto.LoginDTO;
import com.isa.projekcije.model.dto.RegistrationDTO;
import com.isa.projekcije.model.fanzone.FanZoneAdmin;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;


    }

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public User createUser(RegistrationDTO registrationDTO) {
        User registeredUser = registrationDTO.createNewUser();
        System.out.println(registeredUser.getFirstName());
        return userRepository.save(registeredUser);
    }

    public boolean emailExists(RegistrationDTO registrationDTO) {
        return userRepository.findByEmail(registrationDTO.getEmail()) != null;
    }

    public User findUser(LoginDTO loginDTO) {
        System.out.println(userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword()));
        return userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
    }

    public List<User> getAllUsers() {
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

    public InstitutionAdmin createInstitutionAdmin(RegistrationDTO registrationDTO) {
        InstitutionAdmin institutionAdmin = InstitutionAdmin.createNewInstitutionAdmin(registrationDTO);
        return (InstitutionAdmin) userRepository.save(institutionAdmin);
    }

    public void setCurrentUser(User user) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        Authentication authentication = new PreAuthenticatedAuthenticationToken(user.getId(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            Long id = Long.parseLong(auth.getName());
            return userRepository.findById(id).orElseThrow(RuntimeException::new);
        } catch (Exception e) {
            return null;
        }
    }

    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }


    public User save(User usr) {
        return userRepository.save(usr);
    }

    public List<User> findAll(Sort email) {
        return userRepository.findAll();
    }

    public FanZoneAdmin createFanZoneAdmin(RegistrationDTO registrationDTO) {
        FanZoneAdmin fanZoneAdmin = FanZoneAdmin.createFromUser(registrationDTO);
        return (FanZoneAdmin) userRepository.save(fanZoneAdmin);
    }

    public SystemAdmin createSystemAdmin(RegistrationDTO registrationDTO) {
        SystemAdmin systemAdmin = SystemAdmin.createFromDto(registrationDTO);
        return (SystemAdmin) userRepository.save(systemAdmin);
    }
}
