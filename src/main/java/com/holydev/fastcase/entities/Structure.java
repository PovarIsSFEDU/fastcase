package com.holydev.fastcase.entities;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "structures")
public class Structure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;


    @ManyToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<User> employees;
}
