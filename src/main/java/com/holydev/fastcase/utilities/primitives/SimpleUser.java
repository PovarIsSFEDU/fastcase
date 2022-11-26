package com.holydev.fastcase.utilities.primitives;


import com.holydev.fastcase.entities.User;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SimpleUser implements Serializable {
    private Long id;
    private String username;
    private String fio;
    private String jwt;

    public SimpleUser(User user, String jwt) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.jwt = jwt;
        this.fio = user.getFio();
    }
}

