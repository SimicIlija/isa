package com.isa.projekcije.service;

import com.isa.projekcije.repository.ThemePropsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThemePropsService {
    @Autowired
    private ThemePropsRepository themePropsRepository;
}
