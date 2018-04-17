package com.isa.projekcije.converters;

import com.isa.projekcije.model.User;
import com.isa.projekcije.model.dto.UserDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserToUserDTO implements Converter<User, UserDTO> {
    @Override
    public UserDTO convert(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setId(user.getId());
        return userDTO;
    }

    public List<UserDTO> convert(List<User> source) {

        List<UserDTO> seatsDTO = new ArrayList<UserDTO>();
        for (User user : source) {
            seatsDTO.add(convert(user));
        }


        return seatsDTO;
    }
}
