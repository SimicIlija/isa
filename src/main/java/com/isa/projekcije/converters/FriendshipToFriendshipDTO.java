package com.isa.projekcije.converters;

import com.isa.projekcije.model.Friendship;
import com.isa.projekcije.model.User;
import com.isa.projekcije.model.dto.FriendshipDTO;
import com.isa.projekcije.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FriendshipToFriendshipDTO implements Converter<Friendship, FriendshipDTO> {

    @Autowired
    UserToUserDTO userToUserDTO;

    @Override
    public FriendshipDTO convert(Friendship friendship) {
        FriendshipDTO friendshipDTO = new FriendshipDTO();
        User sender = friendship.getSender();
        UserDTO senderDTO = userToUserDTO.convert(sender);
        User receiver = friendship.getReceiver();
        UserDTO receiverDTO = userToUserDTO.convert(receiver);
        friendshipDTO.setReceiver(receiverDTO);
        friendshipDTO.setSender(senderDTO);
        friendshipDTO.setAccepted(friendship.isAccepted());
        friendshipDTO.setSent(friendship.isSent());
        friendshipDTO.setId(friendship.getId());
        return friendshipDTO;

    }

    public List<FriendshipDTO> convert(List<Friendship> source) {
        List<FriendshipDTO> friendshipDTOList = new ArrayList<FriendshipDTO>();
        for (Friendship friendship : source) {
            friendshipDTOList.add(convert(friendship));
        }
        return friendshipDTOList;
    }
}
