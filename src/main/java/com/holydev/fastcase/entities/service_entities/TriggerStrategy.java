package com.holydev.fastcase.entities.service_entities;

import com.holydev.fastcase.entities.Task;
import com.holydev.fastcase.entities.User;
import com.holydev.fastcase.utilities.primitives.SimpleTrigger;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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

    //    Кого уведомляем
    @ManyToOne
    @JoinColumn(name = "adressant")
    private User adressant;


    //    Тип триггера (по времени/в зависимости от задачи)
    @Column
    private String triggerType;

    @ManyToOne
    @JoinColumn(name = "parent_task_id")
    private Task parent_task;


    //    В какой задаче нужны изменения состояния
    @ManyToOne
    @JoinColumn(name = "target_task_id")
    private Task target_task;


    @OneToMany(mappedBy = "trigger_strategy", orphanRemoval = true, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Notification> notifications;


    //    Task_opened, task_closed, task_completed
    @Column
    private String needed_action;

    //1h/1d/1w
    private String timer;


    public TriggerStrategy(SimpleTrigger s_trigger) {
        this.triggerType = s_trigger.trigger_type();
        this.needed_action = s_trigger.needed_action();
        this.timer = s_trigger.timer();
    }

    public void addNotification(Notification notification) {
        this.notifications.add(notification);
    }
}
