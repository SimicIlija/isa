package com.isa.projekcije.repository;

import com.isa.projekcije.model.fanzone.ThemeProps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ThemePropsRepository extends JpaRepository<ThemeProps, Long> {

    @Query(value = "SELECT * FROM THEME_PROPS WHERE AMOUNT > 0", nativeQuery = true)
    List<ThemeProps> findAvailable();

    Optional<ThemeProps> findById(long id);
}
