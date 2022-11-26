package com.holydev.fastcase.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.holydev.fastcase.entities.service_entities.Comment;
import com.holydev.fastcase.entities.service_entities.TriggerStrategy;
import com.holydev.fastcase.utilities.primitives.SimpleTask;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String attachment_path;


    //     0 - opened, 1 - closed, 2 - completed
    @Column
    private int status;

    @Column
    private int points;

    @Column
    private String est_time;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonBackReference
    private User author_id;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "user_tasks")
    @JsonIgnore
    private Set<User> assignee_ids = new java.util.LinkedHashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "subscribed_tasks")
    @JsonIgnore
    private Set<User> interesants = new java.util.LinkedHashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "target_task", orphanRemoval = true)
    @JsonIgnore
    private Set<TriggerStrategy> triggers = new java.util.LinkedHashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parent_task", orphanRemoval = true)
    @JsonIgnore
    private Set<TriggerStrategy> parent_triggers = new java.util.LinkedHashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parent_task", orphanRemoval = true)
    @JsonIgnore
    private Set<Comment> comments = new java.util.LinkedHashSet<>();

    @JsonIgnore
    public Task(SimpleTask new_task) {
        this.name = new_task.name();
        this.description = new_task.description();
        this.attachment_path = new_task.media_contents();
        this.points = new_task.points();
    }

    @JsonIgnore
    public void add_assignee(User assignee) {
        this.assignee_ids.add(assignee);
    }
}
