package com.holydev.fastcase.services.realisation;

import com.holydev.fastcase.entities.Role;
import com.holydev.fastcase.entities.Task;
import com.holydev.fastcase.entities.User;
import com.holydev.fastcase.entities.service_entities.TriggerStrategy;
import com.holydev.fastcase.repos.TaskRepo;
import com.holydev.fastcase.services.interfaces.TaskServiceInterface;
import com.holydev.fastcase.utilities.primitives.SearchRequest;
import com.holydev.fastcase.utilities.primitives.SimpleTask;
import com.holydev.fastcase.utilities.primitives.SimpleTrigger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
    public void addTrigger(SimpleTrigger s_trigger, User author) {
        var trigger = new TriggerStrategy(s_trigger);
        trigger.setAdressant(author);
//        TODO Создать нотификацию
        var trigger_strat = triggerService.save(trigger);
    }

    @Override
    public List<Task> getOpenTasksForUser(User author) {
        return taskRepo.findByStatus(author.getId());
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
                trigger.setAdressant(userService.getUserById(s_trigger.author_id()));
//                TODO создать нотификацию
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
//                    sendNotification(author, assignee, task_id, CustomNotificationContentSample.TASK_INVITATION);
                    System.out.println("NIY!");
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    private boolean canAssign(User author, User assignee) {
        Role author_role = (Role) (author.getAuthorities()).iterator().next();
        Role assignee_role = (Role) (assignee.getAuthorities()).iterator().next();
        return author_role.getId() <= assignee_role.getId();
    }

    public List<Task> searchTasks(SearchRequest search_req) {
        return taskRepo.findAllByStr(search_req.search_value());
    }
}
