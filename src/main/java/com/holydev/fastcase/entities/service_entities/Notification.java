package com.holydev.fastcase.entities.service_entities;


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

    // 0 - open, 1 - closed
    @Column
    private int status;

    @Column
    private String type;

    @Column
    private int tries;

    @Column
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;


}
