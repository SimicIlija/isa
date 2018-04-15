package com.isa.projekcije.repository;

import com.isa.projekcije.model.fanzone.UserProps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserPropsRepository extends JpaRepository<UserProps, Long> {
    Optional<UserProps> findById(long id);

    @Query(value = "SELECT * FROM USER_PROPS WHERE STATE = 'APPROVED'", nativeQuery = true)
    List<UserProps> findApproved();
}
