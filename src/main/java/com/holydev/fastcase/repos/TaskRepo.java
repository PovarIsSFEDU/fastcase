package com.holydev.fastcase.repos;

import com.holydev.fastcase.entities.Task;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TaskRepo extends PagingAndSortingRepository<Task, Long> {

}
