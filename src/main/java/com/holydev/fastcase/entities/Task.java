package com.holydev.fastcase.entities;

import com.holydev.fastcase.entities.service_entities.Comment;
import com.holydev.fastcase.entities.service_entities.TriggerStrategy;
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


    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author_id;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee_id;

    @ManyToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<User> interesants;

    @OneToMany(mappedBy = "parent_task", orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<TriggerStrategy> triggers;

    @OneToMany(mappedBy = "parent_task", orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Comment> comments;

}
