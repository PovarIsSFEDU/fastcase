package com.holydev.fastcase.entities;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
public class Role implements GrantedAuthority {
    public static final String BOSS = "BOSS";

    public static final String TECHNICIAN = "TECHNICIAN";

    public static final String USER = "USER";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
    private Set<User> users = new java.util.LinkedHashSet<>();

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public java.lang.String getAuthority() {
        return getName();
    }
}
