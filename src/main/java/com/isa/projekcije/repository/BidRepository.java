package com.isa.projekcije.repository;

import com.isa.projekcije.model.fanzone.Bid;
import com.isa.projekcije.model.fanzone.BidId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, BidId> {
    Optional<Bid> findByBidId(BidId bidId);

    List<Bid> findByUserProps_Id(long id);

    List<Bid> findByBidder_Id(long id);

    List<Bid> findByUserProps_Creator_Id(long id);
}
