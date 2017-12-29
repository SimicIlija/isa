package com.isa.projekcije.service;

import com.isa.projekcije.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.isa.projekcije.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }
}
