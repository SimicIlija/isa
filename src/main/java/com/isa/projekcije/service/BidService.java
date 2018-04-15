package com.isa.projekcije.service;

import com.isa.projekcije.model.fanzone.Bid;
import com.isa.projekcije.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidService {
    @Autowired
    private BidRepository bidRepository;

    public List<Bid> findAll() {
        return bidRepository.findAll();
    }
}
