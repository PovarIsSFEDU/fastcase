package com.holydev.fastcase.repos;

import com.holydev.fastcase.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<Task, Long> {

}
