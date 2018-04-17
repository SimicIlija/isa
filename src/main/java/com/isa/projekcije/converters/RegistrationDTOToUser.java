package com.isa.projekcije.converters;

import com.isa.projekcije.model.User;
import com.isa.projekcije.model.dto.RegistrationDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RegistrationDTOToUser implements Converter<RegistrationDTO, User> {

    @Override
    public User convert(RegistrationDTO registrationDTO) {
        User user = new User();
        user.setFirstName(registrationDTO.getFirstName());
        user.setPhoneNumber(registrationDTO.getPhoneNumber());
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(registrationDTO.getPassword());

        return user;
    }
}
