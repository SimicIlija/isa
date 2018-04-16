package com.isa.projekcije.controller;

import com.isa.projekcije.model.User;
import com.isa.projekcije.model.fanzone.Bid;
import com.isa.projekcije.model.fanzone.BidState;
import com.isa.projekcije.model.fanzone.UserProps;
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
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/up/{id}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addNewBid(@PathVariable("id") long id, @Valid @RequestBody double price) {
        try {
            UserProps userProps = userPropsService.findById(id);
            User user = userService.findById(2);
            Bid bid = new Bid();
            bid.setBidder(user);
            bid.setUserProps(userProps);
            bid.setPrice(price);
            bid.setBidState(BidState.DEFAULT);
            bid = bidService.save(bid);
            return new ResponseEntity(bid, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
