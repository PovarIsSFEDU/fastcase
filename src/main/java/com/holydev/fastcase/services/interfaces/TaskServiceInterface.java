package com.holydev.fastcase.services.interfaces;

import com.holydev.fastcase.entities.Task;
import com.holydev.fastcase.entities.service_entities.TriggerStrategy;

import java.util.Optional;

public interface TaskServiceInterface {
    Optional<Task> getTaskById(Long id);

    void createTask(Task new_task);

    void openTask(Long task_id);

    void closeTask(Long task_id);

    void completeTask(Long task_id);

    void updateTask(Long task_id, Task new_task);

    void deleteTask(Long task_id);

    void addTrigger(Long task_id, TriggerStrategy trigger);
}
