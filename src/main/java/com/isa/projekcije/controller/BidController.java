package com.isa.projekcije.controller;

import com.isa.projekcije.model.User;
import com.isa.projekcije.model.dto.BidDTO;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
     * GET api/bid/bidder/
     * Returns all bids where bidder id is equal to id
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/bidder/", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getByBidderId() {
        try {
            User user = userService.getCurrentUser();
            List<Bid> bids = bidService.findByBidderId(user.getId());
            List<BidDTO> retVal = bids.stream().map(BidDTO::createFromBid).collect(Collectors.toList());
            return new ResponseEntity<>(retVal, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET api/bid/creator/
     * Returns all bids where creator of user props id is equal to id
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/creator/", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getByCreatorId() {
        try {
            User currentUser = userService.getCurrentUser();
            List<Bid> bids = bidService.findByCreatorId(currentUser.getId());
            List<BidDTO> retVal = bids.stream().map(BidDTO::createFromBid).collect(Collectors.toList());
            return new ResponseEntity<>(retVal, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * POST api/bid/up/{id}
     * Creating a bid for user props.
     */
    @PreAuthorize("isAuthenticated()")
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
            User user = userService.getCurrentUser();
            if (userProps.getCreator().getId().equals(user.getId())) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
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
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/up/{id}", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity modifyBid(@PathVariable("id") long id, @Valid @RequestBody double price) {
        try {
            UserProps userProps = userPropsService.findById(id);
            User user = userService.getCurrentUser();
            Bid bid = bidService.findById(userProps.getId(), user.getId());
            if (bidService.isAccepted(userProps.getId())) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            if (userProps.getCreator().getId().equals(user.getId())) {
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
