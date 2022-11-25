package com.holydev.fastcase.repos;

import com.holydev.fastcase.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    User findByEmail(String email);


    @EntityGraph(attributePaths = {"friendlist", "author_tasks", "user_comments", "triggers", "reffered_notifications", "incoming_notifications", "roles", "user_tasks", "subscribed_tasks", "structures_list"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<User> findFirstByUsername(String username);


    @EntityGraph(attributePaths = {"friendlist.structures_list"})
    @Query("select u from User u where u.id = ?1")
    Optional<User> findFriendlistById(Long user_id);
}
