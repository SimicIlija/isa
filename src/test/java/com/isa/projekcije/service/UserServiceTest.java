package com.isa.projekcije.service;

import com.isa.projekcije.ProjekcijeApplicationTests;
import com.isa.projekcije.model.Role;
import com.isa.projekcije.model.User;
import com.isa.projekcije.model.dto.LoginDTO;
import com.isa.projekcije.model.dto.RegistrationDTO;
import javassist.NotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class UserServiceTest extends ProjekcijeApplicationTests {

    @Autowired
    UserService userService;


    @Test
    public void emailAlreadyInUseShouldReturnTrue(){
        final RegistrationDTO registrationDTO = new RegistrationDTO("Marko", "Markovic", "021/858-988", "jelena@gmail.com", "newPassword", "newPassword");
        final boolean emailInUse = userService.emailExists(registrationDTO);
        assertThat(emailInUse).isEqualTo(true);

    }

    @Test
    public void createNewUserShouldReturnUser(){
        final RegistrationDTO registrationDTO = new RegistrationDTO("Marko", "Markovic", "021/858-988", "marko@gmail.com", "newPassword", "newPassword");
        final User createdUser = userService.createUser(registrationDTO);
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getRole()).isEqualTo(Role.REGISTERED);
        assertThat(createdUser.getFirstName()).isEqualTo(registrationDTO.getFirstName());
        assertThat(createdUser.getLastName()).isEqualTo(registrationDTO.getLastName());
        assertThat(createdUser.getEmail()).isEqualTo(registrationDTO.getEmail());
        assertThat(createdUser.getPassword()).isEqualTo(registrationDTO.getPassword());

    }

    @Test
    public void loginWithExistingAccountSchouldReturnUser(){
        final LoginDTO loginDTO = new LoginDTO("jelena@gmail.com","jelena");
        final User foundUser = userService.findUser(loginDTO);
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getPassword()).isEqualTo(loginDTO.getPassword());
        assertThat(foundUser.getEmail()).isEqualTo(loginDTO.getEmail());

    }

    @Test
    public void loginWithNonExistingAccountShouldReturnNull(){
        final LoginDTO loginDTO = new LoginDTO("marko@gmail.com","marko");
        final User user = userService.findUser(loginDTO);
        assertThat(user).isNull();
    }
}
