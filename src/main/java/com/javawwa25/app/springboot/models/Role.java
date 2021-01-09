package com.javawwa25.app.springboot.models;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long role_id;

    private String role_name;

    public Role(String role_name) {
        this.role_name = role_name;
    }

}
