package com.isa.projekcije.service;

import com.isa.projekcije.model.Friendship;
import com.isa.projekcije.model.User;
import com.isa.projekcije.repository.FriendshipRepository;
import com.isa.projekcije.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FriendshipService {

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UserRepository userRepository;

    public Friendship create(User sender, Long receiverId) {
        final User receiver = userRepository.findById(receiverId);
        Friendship existingFriendship = friendshipRepository.findBySenderIdAndReceiverId(sender.getId(), receiver.getId());
        Friendship existingFriendshipReverse = friendshipRepository.findBySenderIdAndReceiverId(receiver.getId(), sender.getId());

        if (existingFriendship != null || existingFriendshipReverse != null) {
            return null;
        }
        final Friendship friendship = new Friendship(sender, receiver);
        return friendshipRepository.save(friendship);
    }


}
