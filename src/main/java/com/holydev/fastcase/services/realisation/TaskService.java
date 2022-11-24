package com.holydev.fastcase.services.realisation;

import com.holydev.fastcase.entities.Role;
import com.holydev.fastcase.entities.Task;
import com.holydev.fastcase.entities.User;
import com.holydev.fastcase.entities.service_entities.Notification;
import com.holydev.fastcase.entities.service_entities.TriggerStrategy;
import com.holydev.fastcase.repos.TaskRepo;
import com.holydev.fastcase.services.interfaces.TaskServiceInterface;
import com.holydev.fastcase.utilities.customs.CustomNotificationStatus;
import com.holydev.fastcase.utilities.customs.CustomNotificationType;
import com.holydev.fastcase.utilities.primitives.SimpleTask;
import com.holydev.fastcase.utilities.primitives.SimpleTrigger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService implements TaskServiceInterface {

    private final TaskRepo taskRepo;

    private final UserService userService;

    private final NotificationService notificationService;

    private final TriggerService triggerService;

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

    @Override
    public Optional<Task> createTask(SimpleTask new_task, User author, List<Long> assigned_users_id, List<Long> subscribed_users_id, List<SimpleTrigger> triggers) {
        try {
            var task = new Task(new_task);

            task.setAuthor_id(author);
            task.setStatus(1);

            var assignees = assigned_users_id.stream().map(userService::getUserById).collect(Collectors.toSet());
            task.setAssignee_ids(assignees);

            var interesants = subscribed_users_id.stream().map(userService::getUserById).collect(Collectors.toSet());
            task.setInteresants(interesants);

            var trigger_strats = new ArrayList<TriggerStrategy>();
            for (var s_trigger : triggers) {
                var trigger = new TriggerStrategy(s_trigger);
                trigger.setOwner(userService.getUserById(s_trigger.author_id()));
                if (s_trigger.parent_task_id() != null) {
                    trigger.setParent_task(getTaskById(s_trigger.parent_task_id()).orElseThrow());
                }
                var trigger_strat = triggerService.save(trigger);
                trigger_strats.add(trigger_strat);
            }
            task.setTriggers(new HashSet<>(trigger_strats));

            return Optional.of(createAndFlushTask(task));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Boolean addAssigneeOrSendNotificationByPermission(User author, Long task_id, List<Long> assignee_ids) {
        try {
            var task = taskRepo.findById(task_id).orElseThrow();
            for (var assignee_id : assignee_ids) {
                var assignee = userService.getUserById(assignee_id);
                if (canAssign(author, assignee)) {
                    task.add_assignee(assignee);
                } else {
                    sendNotification(author, assignee, task_id, CustomNotificationType.INVITATION);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void sendNotification(User author, User assignee, Long task_id, String body) {
        var new_notification = new Notification(author, assignee, body, this.getTaskById(task_id).orElseThrow(), CustomNotificationStatus.PREPARED);
        notificationService.send(new_notification);
    }

    private boolean canAssign(User author, User assignee) {
        Role author_role = (Role) (author.getAuthorities()).iterator().next();
        Role assignee_role = (Role) (assignee.getAuthorities()).iterator().next();
        return author_role.getId() <= assignee_role.getId();
    }
}
