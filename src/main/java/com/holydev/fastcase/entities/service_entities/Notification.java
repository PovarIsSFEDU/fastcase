package com.holydev.fastcase.entities.service_entities;


import com.holydev.fastcase.entities.Task;
import com.holydev.fastcase.entities.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "referrer")
    private User referrer;

    @ManyToOne
    @JoinColumn(name = "addressant")
    private User addressant;

    @ManyToOne
    @JoinColumn(name = "trigger_strategy")
    private TriggerStrategy trigger_strategy;


    @ManyToOne
    @JoinColumn(name = "referred_task")
    private Task referred_task;


    @Column
    private String status;

    @Column
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;


    public Notification(User author, User assignee, String body, Task referred_task, String status) {
        this.referrer = author;
        this.addressant = assignee;
        this.referred_task = referred_task;
        this.content = String.format(body, author.getFio(), assignee.getFio(), referred_task.getId());
        this.status = status;
    }
}
