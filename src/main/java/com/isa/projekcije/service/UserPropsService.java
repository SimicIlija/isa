package com.isa.projekcije.service;

import com.isa.projekcije.model.fanzone.UserProps;
import com.isa.projekcije.repository.UserPropsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPropsService {
    @Autowired
    private UserPropsRepository userPropsRepository;

    public List<UserProps> findAll() {
        return userPropsRepository.findAll();
    }

    public UserProps findById(long id) {
        return userPropsRepository.findById(id).orElseThrow(() -> new RuntimeException());
    }

    public UserProps create(UserProps userProps) {
        return userPropsRepository.save(userProps);
    }

    public UserProps update(UserProps userProps) {
        return userPropsRepository.save(userProps);
    }
}
