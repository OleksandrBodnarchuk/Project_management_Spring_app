package com.javawwa25.app.springboot.models;

import java.util.Collection;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;

    private String first_name;

    private String last_name;

    private String email;

    private boolean isActive;
    private String activationCode;

    private String password;

    // no ID constructor
    public User(String first_name, String last_name, String email, String password, Collection<Role> user_roles) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.user_roles = user_roles;
    }

    // mapping user with roles
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> user_roles;

    // mapping user with projects
    @OneToMany(mappedBy = "user")
    private Set<Project> user_projects;

    // mapping user with tasks
    @OneToMany(mappedBy = "user")
    private Set<Task> tasks;





}
