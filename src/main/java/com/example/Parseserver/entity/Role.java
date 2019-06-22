package com.example.Parseserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private int id;
    @Column(name = "role")
    private String role;

//    @ManyToMany(mappedBy = "role")
//    private List<Client> clients = new ArrayList<>();

    public int getId() {
        return id;
    }

//    public List<Client> getClients() {
//        return clients;
//    }
//
//    public void setClients(List<Client> clients) {
//        this.clients = clients;
//    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
