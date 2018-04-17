package com.isa.projekcije.service;

import com.isa.projekcije.repository.ReservationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationsService {
    @Autowired
    private ReservationsRepository reservationsRepository;


}
