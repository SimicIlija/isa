package com.isa.projekcije.repository;

import com.isa.projekcije.model.fanzone.Bid;
import com.isa.projekcije.model.fanzone.BidId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, BidId> {
}
