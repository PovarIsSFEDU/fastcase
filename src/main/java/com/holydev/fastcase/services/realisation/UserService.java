package com.holydev.fastcase.services.realisation;

import com.holydev.fastcase.entities.Task;
import com.holydev.fastcase.entities.User;
import com.holydev.fastcase.entities.service_entities.TriggerStrategy;
import com.holydev.fastcase.repos.UserRepo;
import com.holydev.fastcase.utilities.primitives.RegistrationRequest;
import com.holydev.fastcase.utilities.primitives.SimpleTask;
import com.holydev.fastcase.utilities.primitives.SimpleTrigger;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;

    private final RoleService roleService;

    private final TaskService taskService;

    private final TriggerService triggerService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("There is no user with such username!");
        });
    }

    public User getUserById(Long user_id) {
        return userRepo.findById(user_id).orElseThrow();
    }

    public User findAllInfoByUsername(String username) {
        return userRepo.findFirstByUsername(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("There is no user with such username!");
        });
    }

    public void save(User user) {
        try {
            userRepo.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User register(RegistrationRequest request) {
        var new_user = new User(request);
        userRepo.save(new_user);
        return null;
    }

    public Optional<Task> createTask(SimpleTask new_task, User author, List<Long> assigned_users_id, List<Long> subscribed_users_id, List<SimpleTrigger> triggers) {
        try {
            var task = new Task(new_task);

            task.setAuthor_id(author);
            task.setStatus(1);

            var assignees = assigned_users_id.stream().map(this::getUserById).collect(Collectors.toSet());
            task.setAssignee_ids(assignees);

            var interesants = subscribed_users_id.stream().map(this::getUserById).collect(Collectors.toSet());
            task.setInteresants(interesants);

            var trigger_strats = new ArrayList<TriggerStrategy>();
            for (var s_trigger : triggers) {
                var trigger = new TriggerStrategy(s_trigger);
                trigger.setOwner(this.getUserById(s_trigger.author_id()));
                if (s_trigger.parent_task_id() != null) {
                    trigger.setParent_task(taskService.getTaskById(s_trigger.parent_task_id()).orElseThrow());
                }
                var trigger_strat = triggerService.save(trigger);
                trigger_strats.add(trigger_strat);
            }
            task.setTriggers(new HashSet<>(trigger_strats));

            return Optional.of(taskService.createAndFlushTask(task));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void createDefault() {
        var reg_req = new RegistrationRequest("admin", "admin", "Лукаш Павел Андреевич", "plukash@sfedu.ru", "+79281912174");
        var admin = new User(reg_req);
        admin.setRoles(Collections.singleton(roleService.getRoleByName("BOSS")));
        save(admin);
    }
}
