package com.isa.projekcije.repository;

import com.isa.projekcije.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User save(User user);
    <E extends User> Optional<E> findByEmail(String email);
    <E extends User> Optional<E> findByEmailAndPassword(String email,String password);


}
