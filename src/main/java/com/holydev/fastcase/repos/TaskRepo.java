package com.holydev.fastcase.repos;

import com.holydev.fastcase.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {
    @Query("select t from Task t where t.author_id.id = ?1 and (t.status = 0 or t.status = 1)")
    List<Task> findByStatus(Long author_id);


}
