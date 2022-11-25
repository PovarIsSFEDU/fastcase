package com.holydev.fastcase.repos;

import com.holydev.fastcase.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {
    @Query("select t from Task t where t.author_id.id = ?1 and (t.status = 0 or t.status = 1)")
    List<Task> findByStatus(Long author_id);

    @Query("select t from Task t where (t.status = 0 or t.status = 1) and (t.author_id.fio like concat('%', ?1, '%') or " +
           "t.name like concat('%', ?1, '%') or t.description like concat('%', ?1, '%'))")
    List<Task> findAllByStr(String search_str);


}
