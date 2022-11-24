package com.holydev.fastcase.entities.service_entities;

import com.holydev.fastcase.entities.Task;
import com.holydev.fastcase.entities.User;
import com.holydev.fastcase.utilities.primitives.SimpleTrigger;
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

    @ManyToOne
    @JoinColumn(name = "owner")
    private User owner;


    //    Тип триггера (по времени/в зависимости от задачи)
    @Column
    private String triggerType;

    @ManyToOne
    @JoinColumn(name = "parent_task")
    private Task parent_task;

    @Column
    private String needed_action;

    //
    private String timer;


    public TriggerStrategy(SimpleTrigger s_trigger) {
        this.triggerType = s_trigger.trigger_type();
        this.needed_action = s_trigger.needed_action();
        this.timer = s_trigger.timer();
    }
}
