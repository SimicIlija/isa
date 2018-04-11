package com.isa.projekcije.service;

import com.isa.projekcije.model.Show;
import com.isa.projekcije.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    public Show findOne(Long id) {
        return showRepository.findOne(id);
    }

    public Show save(Show show) {
        return showRepository.save(show);
    }

    public Show delete(Long id) {
        Show show = showRepository.findOne(id);
        if (show == null) {
            return null;
        }
        showRepository.delete(show);
        return show;
    }
}
