package com.isa.projekcije.service;

import com.isa.projekcije.model.fanzone.UserProps;
import com.isa.projekcije.model.fanzone.UserPropsState;
import com.isa.projekcije.repository.UserPropsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<UserProps> findApproved() {
        List<UserProps> list = userPropsRepository.findByState(UserPropsState.APPROVED);
        List<UserProps> retVal = null;
        retVal = list.stream().filter(UserProps::isDateOk).collect(Collectors.toList());
        return retVal;
    }

    public List<UserProps> findUnchecked() {
        List<UserProps> list = userPropsRepository.findByState(UserPropsState.UNCHECKED);
        List<UserProps> retVal = null;
        retVal = list.stream().filter(UserProps::isDateOk).collect(Collectors.toList());
        return retVal;
    }
}
