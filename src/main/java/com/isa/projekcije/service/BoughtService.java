package com.isa.projekcije.service;

import com.isa.projekcije.model.fanzone.Bought;
import com.isa.projekcije.model.fanzone.BoughtId;
import com.isa.projekcije.repository.BoughtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoughtService {
    @Autowired
    private BoughtRepository boughtRepository;

    public Bought findOne(long tpId, long uId) {
        BoughtId boughtId = new BoughtId(tpId, uId);
        return boughtRepository.findOne(boughtId);
    }

    public List<Bought> findByUserId(long id) {
        return boughtRepository.findByBuyer_Id(id);
    }

    public void update(Bought bought) {
        boughtRepository.save(bought);
    }

    public void create(Bought bought) {
        boughtRepository.save(bought);
    }
}
