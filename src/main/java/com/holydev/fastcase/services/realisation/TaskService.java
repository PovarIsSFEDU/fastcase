package com.holydev.fastcase.services.realisation;

import com.holydev.fastcase.entities.Task;
import com.holydev.fastcase.entities.service_entities.TriggerStrategy;
import com.holydev.fastcase.repos.TaskRepo;
import com.holydev.fastcase.services.interfaces.TaskServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService implements TaskServiceInterface {

    private final TaskRepo taskRepo;

    @Override
    public Optional<Task> getTaskById(Long id) {
        return taskRepo.findById(id);
    }

    @Override
    public void createTask(Task new_task) {
        taskRepo.save(new_task);
    }

    public Task createAndFlushTask(Task new_task) {
        return taskRepo.saveAndFlush(new_task);
    }

    @Override
    public void openTask(Long task_id) {
    }

    @Override
    public void closeTask(Long task_id) {
    }

    @Override
    public void completeTask(Long task_id) {
    }

    @Override
    public void updateTask(Long task_id, Task new_task) {
        new_task.setId(task_id);
        taskRepo.save(new_task);
    }

    @Override
    public void deleteTask(Long task_id) {
        taskRepo.deleteById(task_id);
    }

    @Override
    public void addTrigger(Long task_id, TriggerStrategy trigger) {

    }
}
