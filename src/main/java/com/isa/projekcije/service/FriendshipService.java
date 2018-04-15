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

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendshipService {

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public Friendship create(User sender, String email) {
        final User receiver = userRepository.findByEmail(email);
        Friendship existingFriendship = friendshipRepository.findBySenderIdAndReceiverId(sender.getId(), receiver.getId());
        Friendship existingFriendshipReverse = friendshipRepository.findBySenderIdAndReceiverId(receiver.getId(), sender.getId());

        if (sender.getEmail().equals(receiver.getEmail())) {
            return null;
        }
        if (existingFriendship != null || existingFriendshipReverse != null) {
            return null;
        }
        final Friendship friendship = new Friendship(sender, receiver);
        return friendshipRepository.save(friendship);
    }


    public Friendship accept(long senderId, long receiverId) {
        final Friendship friendship = friendshipRepository.findBySenderIdAndReceiverId(senderId, receiverId);
        friendship.setAccepted(true);
        return friendshipRepository.save(friendship);
    }

    public List<Friendship> getFriendsNotAccepted() {
        List<Friendship> friendships = friendshipRepository.findBySenderId(userService.getCurrentUser().getId());
        List<Friendship> pendingFriendships = new ArrayList<Friendship>();
        for (Friendship f : friendships) {
            if (!(f.isAccepted())) {
                pendingFriendships.add(f);
            }
        }
        return pendingFriendships;
    }
}
