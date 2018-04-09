package com.isa.projekcije.service;

import com.isa.projekcije.model.RegisteredUser;
import com.isa.projekcije.model.User;
import com.isa.projekcije.model.dto.LoginDTO;
import com.isa.projekcije.model.dto.RegistrationDTO;
import com.isa.projekcije.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


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

}
