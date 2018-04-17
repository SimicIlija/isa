package com.isa.projekcije.service;

import com.isa.projekcije.converters.FriendshipToFriendshipDTO;
import com.isa.projekcije.converters.UserDTOToUser;
import com.isa.projekcije.converters.UserToUserDTO;
import com.isa.projekcije.model.Friendship;
import com.isa.projekcije.model.User;
import com.isa.projekcije.model.dto.FriendshipDTO;
import com.isa.projekcije.model.dto.UserDTO;
import com.isa.projekcije.repository.FriendshipRepository;
import com.isa.projekcije.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.data.domain.Sort;
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
    private UserToUserDTO userToUserDTO;
    @Autowired
    FriendshipToFriendshipDTO friendshipToFriendshipDTO;
    @Autowired
    private UserDTOToUser userDTOToUser;

    @Autowired
    private UserService userService;

    public Friendship create(User sender, String email) {
        final User receiver = userService.findByEmail(email);
        Friendship existingFriendship = friendshipRepository.findBySenderIdAndReceiverId(sender.getId(), receiver.getId());
        Friendship existingFriendshipReverse = friendshipRepository.findBySenderIdAndReceiverId(receiver.getId(), sender.getId());

        if (sender.getEmail().equals(receiver.getEmail())) {
            return null;
        }
        if (existingFriendship != null || existingFriendshipReverse != null) {
            return null;
        }
        final Friendship friendship = new Friendship(sender, receiver);
        friendship.setAccepted(false);
        friendship.setSent(true);
        return friendshipRepository.save(friendship);
    }


    public Friendship accept(long senderId, long receiverId) {
        final Friendship friendship = friendshipRepository.findBySenderIdAndReceiverId(senderId, receiverId);
        friendship.setAccepted(true);
        return friendshipRepository.save(friendship);
    }


    public List<FriendshipDTO> getAllFriendships() {
        List<Friendship> friendships = friendshipRepository.findBySenderId(userService.getCurrentUser().getId());
        List<FriendshipDTO> friendshipDTOList = friendshipToFriendshipDTO.convert(friendships);

        return friendshipDTOList;
    }

    public List<Friendship> findBySenderId(long id) {
        return friendshipRepository.findBySenderId(id);
    }

    public List<FriendshipDTO> getAllRequests() {
        User loggedIn = userService.getCurrentUser();
        List<Friendship> friendships = friendshipRepository.findByReceiverId(loggedIn.getId());
        List<Friendship> accepted = new ArrayList<Friendship>();

        for (Friendship f : friendships) {
            if (f.isAccepted()) {
                accepted.add(f);
            }
        }
        friendships.removeAll(accepted);
        List<FriendshipDTO> friendshipDTOList = new ArrayList<FriendshipDTO>();
        if (friendships != null) {
            friendshipDTOList = friendshipToFriendshipDTO.convert(friendships);
        }
        return friendshipDTOList;
    }

    public List<Friendship> findBySenderIdOrReceiverId(long id, long id1) {
        return friendshipRepository.findBySenderIdOrReceiverId(id, id);
    }

    public Friendship findBySenderIdAndReceiverId(long id, long id1) {
        return friendshipRepository.findBySenderIdAndReceiverId(id, id1);
    }

    public List<Friendship> findAll() {
        return friendshipRepository.findAll();
    }

    public Friendship delete(Long id) {
        Friendship friendship = friendshipRepository.findOne(id);
        if (friendship == null) {
            return null;
        }
        friendshipRepository.delete(friendship);
        return friendship;
    }

    public List<UserDTO> getAllUsersExceptLoggedIn() {
        List<User> users = userService.findAll(new Sort("email"));
        User user = userService.getCurrentUser();
        for (User u : users) {
            if (u.getEmail().equals(user.getEmail())) {
                users.remove(u);
                break;
            }
        }
        List<Friendship> friendships = findBySenderId(userService.getCurrentUser().getId());
        List<User> toRemove = new ArrayList<User>();
        for (User userNew : users) {
            for (Friendship friendship : friendships) {

                if (friendship.getReceiver().getEmail().equals(userNew.getEmail())) {
                    toRemove.add(userNew);
                }

            }
        }

        User userReceiver = null;
        List<FriendshipDTO> userReceiverFriendships = getAllRequests();
        List<UserDTO> requestRemove = new ArrayList<UserDTO>();
        for (FriendshipDTO friend : userReceiverFriendships) {
            if (friend.getReceiver().getEmail().equals(user.getEmail())) {
                requestRemove.add(friend.getSender());
            }
        }

        if (requestRemove != null) {
            List<User> requestRemoveUser = userDTOToUser.convert(requestRemove);
            users.removeAll(requestRemoveUser);
        }
        List<Friendship> confirmedFriendships = findBySenderIdOrReceiverId(user.getId(), user.getId());
        List<User> confirmRemove = new ArrayList<User>();
        if (confirmedFriendships.size() > 0) {
            for (Friendship friend : confirmedFriendships) {
                if (friend.getReceiver().getEmail().equals(user.getEmail())) {
                    confirmRemove.add(friend.getSender());
                }
            }
        }


        users.removeAll(toRemove);
        users.removeAll(confirmRemove);
        List<UserDTO> usersDTOs = userToUserDTO.convert(users);
        return usersDTOs;
    }
}
