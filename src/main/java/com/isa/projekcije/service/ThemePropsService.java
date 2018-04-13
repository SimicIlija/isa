package com.isa.projekcije.service;

import com.isa.projekcije.model.fanzone.ThemeProps;
import com.isa.projekcije.repository.ThemePropsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ThemePropsService {
    @Autowired
    private ThemePropsRepository themePropsRepository;

    public List<ThemeProps> getAll() {
        return themePropsRepository.findAll();
    }

    public List<ThemeProps> getAvailable() {
        return themePropsRepository.findAvailable();
    }

    public ThemeProps create(ThemeProps themeProps) {
        return themePropsRepository.save(themeProps);
    }

    public ThemeProps findById(long id) {
        return themePropsRepository.findById(id).orElseThrow(() -> new RuntimeException());
    }

    public void delete(ThemeProps themeProps) {
        themePropsRepository.delete(themeProps);
    }

    public void update(ThemeProps themeProps) {
        themePropsRepository.save(themeProps);
    }


}
