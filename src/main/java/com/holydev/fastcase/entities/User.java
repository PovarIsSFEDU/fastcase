package com.holydev.fastcase.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.holydev.fastcase.entities.service_entities.Comment;
import com.holydev.fastcase.entities.service_entities.Notification;
import com.holydev.fastcase.entities.service_entities.TriggerStrategy;
import com.holydev.fastcase.utilities.primitives.RegistrationRequest;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //    ФИО
    @Column
    @Setter(value = AccessLevel.NONE)
    private String fio;

    //    Электронная почта пользователя
    @Column
    @Setter(value = AccessLevel.NONE)
    private String email;

    //    Имя пользователя для входа в систему
    @Column
    @Setter(value = AccessLevel.NONE)
    private String username;

    //    Пароль в bCrypt формате
    @Column
    @Setter(value = AccessLevel.NONE)
    @JsonIgnore
    private String password;


    //    Номер телефона
    @Column
    @Setter(value = AccessLevel.NONE)
    private String phone;


    @Column
    private Long points = 0L;


    //    0 - only notifications, 1 - email, 2 - phone_sms
    @Column
    private int preferred_communication;


    //    Список ролей-должностей пользователя (начальник, техник, работник)
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
    private Set<Role> roles = new java.util.LinkedHashSet<>();

    //    Список друзей пользователя
    @JoinTable(name = "friendlists", joinColumns = {
            @JoinColumn(name = "parent", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "in_list", referencedColumnName = "id", nullable = false)})
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<User> my_friendlist = new java.util.LinkedHashSet<>();


    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "my_friendlist")
    @JsonBackReference
    private Set<User> in_friendlist_of = new java.util.LinkedHashSet<>();

    //    Список задач, созданных пользователем
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "author_id", orphanRemoval = true)
    @JsonManagedReference
    private Set<Task> author_tasks = new java.util.LinkedHashSet<>();

    //    Список задач, которые выполняет сотрудник
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_user_tasks",
            joinColumns = @JoinColumn(name = "assignee_ids_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_tasks_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<Task> user_tasks = new java.util.LinkedHashSet<>();

    //    Список задач, на которые подписан сотрудник
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_subscribed_tasks",
            joinColumns = @JoinColumn(name = "interesants_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "subscribed_tasks_id", referencedColumnName = "id"))
    @JsonIgnore
    @JsonBackReference
    private Set<Task> subscribed_tasks = new java.util.LinkedHashSet<>();

    //    Список отделов, в которых работает сотрудник
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_structures_list",
            joinColumns = @JoinColumn(name = "employees_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "structures_list_id", referencedColumnName = "id"))
    @JsonManagedReference
    private Set<Structure> structures_list = new java.util.LinkedHashSet<>();

    //    Список комментариев пользователя
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "author", orphanRemoval = true)
    @JsonIgnore
    private Set<Comment> user_comments = new java.util.LinkedHashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "adressant", orphanRemoval = true)
    @JsonIgnore
    private Set<TriggerStrategy> triggers = new java.util.LinkedHashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "referrer", orphanRemoval = true)
    @JsonIgnore
    private Set<Notification> reffered_notifications = new java.util.LinkedHashSet<>();


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "addressant", orphanRemoval = true)
    @JsonIgnore
    private Set<Notification> incoming_notifications = new java.util.LinkedHashSet<>();

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return username;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }


    public String getFio() {
        return (this.fio == null || this.fio.isBlank() || this.fio.isEmpty()) ? this.username : this.fio;
    }

    @JsonIgnore
    public void addPoints(Long points) {
        this.points += points;
    }

    @JsonIgnore
    public User(RegistrationRequest reg_req) {
        this.fio = reg_req.fio();
        this.email = reg_req.email();
        this.username = reg_req.username();
        this.password = reg_req.password();
        this.phone = reg_req.phone();
    }

    public void addFriend(User friend) {
        this.my_friendlist.add(friend);
    }

    public void becomeFriend(User principal) {
        this.in_friendlist_of.add(principal);
    }
}
