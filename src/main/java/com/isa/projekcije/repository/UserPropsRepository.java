package com.isa.projekcije.repository;

import com.isa.projekcije.model.fanzone.UserProps;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPropsRepository extends JpaRepository<UserProps, Long> {
    Optional<UserProps> findById(long id);
}
