package com.holydev.fastcase.repos;

import com.holydev.fastcase.entities.service_entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Long> {
}
