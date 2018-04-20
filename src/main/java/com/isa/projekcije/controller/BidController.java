package com.isa.projekcije.controller;

import com.isa.projekcije.model.User;
import com.isa.projekcije.model.fanzone.Bid;
import com.isa.projekcije.model.fanzone.BidState;
import com.isa.projekcije.model.fanzone.UserProps;
import com.isa.projekcije.model.fanzone.UserPropsState;
import com.isa.projekcije.service.BidService;
import com.isa.projekcije.service.UserPropsService;
import com.isa.projekcije.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/bid")
public class BidController {
    @Autowired
    private BidService bidService;

    @Autowired
    private UserPropsService userPropsService;

    @Autowired
    private UserService userService;

    /**
     * GET api/bid/all
     * Returns all bids
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAll() {
        List<Bid> result = bidService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * GET api/bid/up/{id}
     * Returns all bids where user props id is equal to id
     */
    @RequestMapping(value = "/up/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getByUserPropsId(@PathVariable("id") long id) {
        try {
            List<Bid> bids = bidService.findByUserProps(id);
            return new ResponseEntity<>(bids, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET api/bid/bidder/{id}
     * Returns all bids where bidder id is equal to id
     */
    @RequestMapping(value = "/bidder/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getByBidderId(@PathVariable("id") long id) {
        try {
            List<Bid> bids = bidService.findByBidderId(id);
            return new ResponseEntity<>(bids, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET api/bid/creator/{id}
     * Returns all bids where creator of user props id is equal to id
     */
    @RequestMapping(value = "/creator/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getByCreatorId(@PathVariable("id") long id) {
        try {
            List<Bid> bids = bidService.findByCreatorId(id);
            return new ResponseEntity<>(bids, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * POST api/bid/up/{id}
     * Creating a bid for user props.
     */
    @RequestMapping(value = "/up/{id}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addNewBid(@PathVariable("id") long id, @Valid @RequestBody double price) {
        try {
            UserProps userProps = userPropsService.findById(id);
            if (userProps.getState() != UserPropsState.APPROVED) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            if (bidService.isAccepted(userProps.getId())) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            User user = userService.findById(2);
            Bid bid = new Bid();
            bid.setBidder(user);
            bid.setUserProps(userProps);
            bid.setPrice(price);
            bid.setBidState(BidState.DEFAULT);
            bid = bidService.save(bid);
            return new ResponseEntity<>(bid, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * PUT api/bid/up/{id}
     * Updating a bid for user props.
     */
    @RequestMapping(value = "/up/{id}", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity modifyBid(@PathVariable("id") long id, @Valid @RequestBody double price) {
        try {
            UserProps userProps = userPropsService.findById(id);
            User user = userService.findById(2);
            Bid bid = bidService.findById(userProps.getId(), user.getId());
            if (bidService.isAccepted(userProps.getId())) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            bid.setPrice(price);
            bid.setBidState(BidState.DEFAULT);
            bid = bidService.save(bid);
            return new ResponseEntity<>(bid, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * PUT api/bid/up/{id1}/bidder/{id2}
     * Updating a bid state for user props.
     * If accepted is true, then this bid should be accepted, and other bids of same
     */
    @RequestMapping(value = "/up/{id1}/bidder/{id2}", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity changeBidState(@PathVariable("id1") long userPropid,
                                         @PathVariable("id2") long bidderId,
                                         @Valid @RequestBody boolean accepted) {
        try {
            Bid bid = bidService.findById(userPropid, bidderId);
            if (bidService.isAccepted(userPropid)) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            if (accepted) {
                bidService.acceptBid(bid);
            } else {
                bid.setBidState(BidState.REJECTED);
                bid = bidService.save(bid);
            }
            return new ResponseEntity<>(bid, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


}
