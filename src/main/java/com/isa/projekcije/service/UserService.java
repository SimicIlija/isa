package com.isa.projekcije.service;

import com.isa.projekcije.model.RegisteredUser;
import com.isa.projekcije.model.User;
import com.isa.projekcije.model.dto.LoginDTO;
import com.isa.projekcije.model.dto.RegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.isa.projekcije.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public User createUser(RegistrationDTO registrationDTO){
        RegisteredUser registeredUser = registrationDTO.createNewUser();
        return userRepository.save(registeredUser);
    }

    public boolean emailExists(RegistrationDTO registrationDTO){
        return userRepository.findByEmail(registrationDTO.getEmail()).isPresent();
    }

    public User findUser(LoginDTO loginDTO){
        return userRepository.findByEmailAndPassword(loginDTO.getEmail(),loginDTO.getPassword()).orElseThrow(() -> new RuntimeException()) ;
    }

}
