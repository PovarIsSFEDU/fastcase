package com.holydev.fastcase.entities.service_entities;

import com.holydev.fastcase.entities.Task;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "triggers")
public class TriggerStrategy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    //    Тип триггера (по времени/в зависимости от задачи)
    @Column
    private String triggerType;

    @ManyToOne
    @JoinColumn(name = "parent_task")
    private Task parent_task;

    private String timer;

}
