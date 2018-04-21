package com.isa.projekcije.repository;

import com.isa.projekcije.model.fanzone.Bought;
import com.isa.projekcije.model.fanzone.BoughtId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoughtRepository extends JpaRepository<Bought, BoughtId> {
    Bought findOne(BoughtId boughtId);

    List<Bought> findByBuyer_Id(long id);
}
