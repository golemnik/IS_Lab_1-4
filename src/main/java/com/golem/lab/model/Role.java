package com.golem.lab.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Set;


@Entity
@Table(name = "role")
public class Role implements Serializable {
    private Long id;
    private String name;
    private Set<Client> clients;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "roles")
    public Set<Client> getUsers() {
        return clients;
    }

    public void setUsers(Set<Client> clients) {
        this.clients = clients;
    }
}
