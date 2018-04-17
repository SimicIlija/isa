package com.isa.projekcije.converters;

import com.isa.projekcije.model.User;
import com.isa.projekcije.model.dto.UserDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDTOToUser implements Converter<UserDTO, User> {

    @Override
    public User convert(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        return user;
    }

    public List<User> convert(List<UserDTO> source) {

        List<User> seats = new ArrayList<User>();
        for (UserDTO userDTO : source) {
            seats.add(convert(userDTO));
        }

        return seats;
    }
}
