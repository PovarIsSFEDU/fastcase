package com.holydev.fastcase.entities;

import com.holydev.fastcase.entities.service_entities.Comment;
import com.holydev.fastcase.entities.service_entities.TriggerStrategy;
import com.holydev.fastcase.utilities.primitives.SimpleTask;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Lob
    private String description;

    @Column
    private String attachment_path;

    @Column
    private int status;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author_id;

    @ManyToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<User> assignee_ids;

    @ManyToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<User> interesants;

    @OneToMany(mappedBy = "parent_task", orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<TriggerStrategy> triggers;

    @OneToMany(mappedBy = "parent_task", orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Comment> comments;

    public Task(SimpleTask new_task) {
        this.name = new_task.name();
        this.description = new_task.description();
        this.attachment_path = new_task.media_contents();
    }
}
