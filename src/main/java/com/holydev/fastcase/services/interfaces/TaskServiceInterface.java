package com.holydev.fastcase.services.interfaces;

import com.holydev.fastcase.entities.Task;
import com.holydev.fastcase.entities.User;
import com.holydev.fastcase.entities.service_entities.TriggerStrategy;
import com.holydev.fastcase.utilities.primitives.SimpleTask;
import com.holydev.fastcase.utilities.primitives.SimpleTrigger;

import java.util.List;
import java.util.Optional;

public interface TaskServiceInterface {
    Optional<Task> getTaskById(Long id);

    void createTask(Task new_task);

    Optional<Task> createTask(SimpleTask new_task, User author, List<Long> assigned_users_id, List<Long> subscribed_users_id, List<SimpleTrigger> triggers);

    void openTask(Long task_id);

    void closeTask(Long task_id);

    void completeTask(Long task_id);

    void updateTask(Long task_id, Task new_task);

    void deleteTask(Long task_id);

    void addTrigger(Long task_id, TriggerStrategy trigger);
}
