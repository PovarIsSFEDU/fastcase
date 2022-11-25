package com.holydev.fastcase.entities.service_entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.holydev.fastcase.entities.Task;
import com.holydev.fastcase.entities.User;
import lombok.*;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "parent_task")
    private Task parent_task;

    @ManyToOne
    @JoinColumn(name = "author")
    private User author;


    @Column
    private String content;

    @Column
    private String attachment_path;


}
