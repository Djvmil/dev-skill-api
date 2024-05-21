package com.jcdecaux.devskill.repository;

import com.jcdecaux.devskill.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    Optional<Developer> findByEmail(String email);
    List<Developer> findAllByLanguages_Name(String name);
}