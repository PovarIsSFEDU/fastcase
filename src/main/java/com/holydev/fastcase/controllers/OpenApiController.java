package com.holydev.fastcase.controllers;


import com.holydev.fastcase.entities.Role;
import com.holydev.fastcase.entities.Task;
import com.holydev.fastcase.entities.User;
import com.holydev.fastcase.services.interfaces.StorageService;
import com.holydev.fastcase.services.realisation.TaskService;
import com.holydev.fastcase.services.realisation.UserService;
import com.holydev.fastcase.utilities.primitives.SearchRequest;
import com.holydev.fastcase.utilities.primitives.SimpleTask;
import com.holydev.fastcase.utilities.primitives.SimpleTrigger;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "API methods for users")
@RestController
@RequestMapping(path = "api/public")
@RequiredArgsConstructor
public class OpenApiController {

    private final StorageService storageService;

    private final UserService userService;

    private final TaskService taskService;


    @RolesAllowed({Role.BOSS, Role.TECHNICIAN, Role.USER})
    @GetMapping("/files/{username}/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename, @PathVariable String username) {

        Resource file = storageService.loadAsResource(username + "/" + filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


    @RolesAllowed({Role.BOSS, Role.TECHNICIAN, Role.USER})
    @PostMapping("/files/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            var a = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            User principal = userService.getUserById(Long.parseLong(a.getName().split(",")[0]));
            storageService.store(file, principal.getUsername());
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @RolesAllowed({Role.BOSS, Role.TECHNICIAN, Role.USER})
    @GetMapping("/profile")
    public ResponseEntity<User> my_profile() {
        try {
            var a = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            User principal = userService.getUserById(Long.parseLong(a.getName().split(",")[0]));
            return ResponseEntity.ok(principal);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new User());
        }
    }


    //    TODO - реализовать ограничение полей доступа
    @RolesAllowed({Role.BOSS, Role.TECHNICIAN, Role.USER})
    @GetMapping("/{username}/profile")
    public ResponseEntity<User> profile(@PathVariable String username) {
        try {
            User principal = userService.findAllInfoByUsername(username);
            return ResponseEntity.ok(principal);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new User());
        }
    }

    @RolesAllowed({Role.BOSS, Role.TECHNICIAN, Role.USER})
    @PostMapping("/create/task")
    public ResponseEntity<Long> create_task(@RequestBody SimpleTask simple_task) {
        try {
            var a = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            User principal = userService.getUserById(Long.parseLong(a.getName().split(",")[0]));
            var task = taskService.createTask(simple_task, principal, simple_task.assignee_ids(), simple_task.subscribed_ids(), simple_task.triggers());
            return task.map(value -> ResponseEntity.ok(value.getId())).orElseGet(() -> ResponseEntity.ok(null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RolesAllowed({Role.BOSS, Role.TECHNICIAN, Role.USER})
    @PostMapping("/add/task_assignees")
    public ResponseEntity<Boolean> add_assignees(@RequestBody Long task_id, List<Long> assignee_ids) {
        try {
            var a = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            User principal = userService.getUserById(Long.parseLong(a.getName().split(",")[0]));
            return ResponseEntity.ok(taskService.addAssigneeOrSendNotificationByPermission(principal, task_id, assignee_ids));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RolesAllowed({Role.BOSS, Role.TECHNICIAN, Role.USER})
    @PostMapping("/add/trigger_strategy")
    public ResponseEntity<String> add_trigger(@RequestBody SimpleTrigger s_trigger) {
        var a = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User author = userService.getUserById(Long.parseLong(a.getName().split(",")[0]));
        taskService.addTrigger(s_trigger, author);
        return ResponseEntity.ok("Not implemented yet!");
    }

    @RolesAllowed({Role.BOSS, Role.TECHNICIAN, Role.USER})
    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> get_tasks() {
        var a = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User author = userService.getUserById(Long.parseLong(a.getName().split(",")[0]));
        return ResponseEntity.ok(taskService.getOpenTasksForUser(author));
    }

    @RolesAllowed({Role.BOSS, Role.TECHNICIAN, Role.USER})
    @PostMapping("/tasks/search")
    public ResponseEntity<List<Task>> search_tasks(@RequestBody SearchRequest search_req) {
        return ResponseEntity.ok(taskService.searchTasks(search_req));
    }

    @RolesAllowed({Role.BOSS, Role.TECHNICIAN, Role.USER})
    @GetMapping("/task/{id}")
    public ResponseEntity<Task> get_task(@PathVariable Long id) {
        var task = taskService.getTaskById(id);
        return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    @RolesAllowed({Role.BOSS, Role.TECHNICIAN, Role.USER})
    @GetMapping("/friendlist")
    public ResponseEntity<List<User>> get_friendlist() {
        var a = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User principal = userService.getUserById(Long.parseLong(a.getName().split(",")[0]));
        return ResponseEntity.ok(new ArrayList<>(principal.getMy_friendlist()));
    }

    @RolesAllowed({Role.BOSS, Role.TECHNICIAN, Role.USER})
    @PostMapping("/friendlist/add/{friend_id}")
    public ResponseEntity<String> add_to_friendlist(@PathVariable Long friend_id) {
        var a = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User principal = userService.getUserById(Long.parseLong(a.getName().split(",")[0]));
        userService.addToFriendList(principal, friend_id);
        return ResponseEntity.ok("Success");
    }

    @RolesAllowed({Role.BOSS, Role.TECHNICIAN, Role.USER})
    @PostMapping("/task/open")
    public ResponseEntity<String> open_task() {
        return ResponseEntity.ok("Not implemented yet!");
    }

    @RolesAllowed({Role.BOSS, Role.TECHNICIAN, Role.USER})
    @PostMapping("/task/close")
    public ResponseEntity<String> close_task() {
        return ResponseEntity.ok("Not implemented yet!");
    }

    @RolesAllowed({Role.BOSS, Role.TECHNICIAN, Role.USER})
    @PostMapping("/task/complete")
    public ResponseEntity<String> complete_task() {
        return ResponseEntity.ok("Not implemented yet!");
    }

    @RolesAllowed({Role.BOSS, Role.TECHNICIAN, Role.USER})
    @PostMapping("/task/comment")
    public ResponseEntity<String> comment_task() {
        return ResponseEntity.ok("Not implemented yet!");
    }

    @RolesAllowed({Role.BOSS, Role.TECHNICIAN, Role.USER})
    @PostMapping("/check")
    public ResponseEntity<String> check_notifications() {
        return ResponseEntity.ok("Not implemented yet!");
    }


}
