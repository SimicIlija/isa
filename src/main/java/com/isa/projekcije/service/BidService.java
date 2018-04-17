package com.isa.projekcije.service;

import com.isa.projekcije.model.fanzone.Bid;
import com.isa.projekcije.model.fanzone.BidId;
import com.isa.projekcije.model.fanzone.BidState;
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

    public Bid save(Bid bid) {
        return bidRepository.save(bid);
    }

    public Bid findById(long userPropsId, long userId) {
        BidId bidId = new BidId();
        bidId.setUserPropsId(userPropsId);
        bidId.setBidderId(userId);
        return bidRepository.findByBidId(bidId).orElseThrow(RuntimeException::new);
    }

    public List<Bid> findByUserProps(long id) {
        return bidRepository.findByUserProps_Id(id);
    }

    public List<Bid> findByBidderId(long id) {
        return bidRepository.findByBidder_Id(id);
    }

    public List<Bid> findByCreatorId(long id) {
        return bidRepository.findByUserProps_Creator_Id(id);
    }

    public boolean isAccepted(long userPropsId) {
        List<Bid> list = findByUserProps(userPropsId);
        boolean retval = false;
        for (Bid bid : list) {
            if (bid.getBidState() == BidState.ACCEPTED) {
                retval = true;
            }
        }
        return retval;
    }

    public void acceptBid(Bid bid) {
        List<Bid> list = findByUserProps(bid.getUserProps().getId());
        for (Bid itBid : list) {
            if (itBid.getBidder() == bid.getBidder()) {
                itBid.setBidState(BidState.ACCEPTED);
            } else {
                itBid.setBidState(BidState.REJECTED);
            }
            bidRepository.save(itBid);
        }
    }
}
