package com.holydev.fastcase.repos;

import com.holydev.fastcase.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    User findByEmail(String email);


    @EntityGraph(attributePaths = {"podvigs", "actions", "status_changes"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<User> findFirstByUsername(String username);
}
