package com.holydev.fastcase.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.holydev.fastcase.entities.service_entities.Comment;
import com.holydev.fastcase.entities.service_entities.TriggerStrategy;
import com.holydev.fastcase.utilities.primitives.RegistrationRequest;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
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

    //    Список ролей-должностей пользователя (начальник, техник, работник)
    @ManyToMany
    @JsonManagedReference
    @Setter
    private Set<Role> roles = new HashSet<>();

    //    Список друзей пользователя
    @OneToMany(mappedBy = "id")
    @ToString.Exclude
    private Set<User> friendlist;

    //    Список задач, созданных пользователем
    @OneToMany(mappedBy = "author_id", orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Task> author_tasks;

    //    Список задач, которые выполняет сотрудник
    @ManyToMany(mappedBy = "assignee_ids", fetch = FetchType.LAZY)
    private Set<Task> user_tasks;

    //    Список задач, на которые подписан сотрудник
    @ManyToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Task> subscribed_tasks;

    //    Список отделов, в которых работает сотрудник
    @ManyToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonManagedReference
    private Set<Structure> structures_list;

    //    Список комментариев пользователя
    @OneToMany(mappedBy = "author", orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Comment> user_comments;

    @OneToMany(mappedBy = "owner", orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<TriggerStrategy> triggers;

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


    public User(RegistrationRequest reg_req) {
        this.fio = reg_req.fio();
        this.email = reg_req.email();
        this.username = reg_req.username();
        this.password = reg_req.password();
        this.phone = reg_req.phone();
    }
}
