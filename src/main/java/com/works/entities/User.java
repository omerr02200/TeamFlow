package com.works.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @Column(length = 100)
    private String  firstName;

    @Column(length = 100)
    private String  lastName;

    @Column(unique = true, length = 250)
    private String email;

    @Column(length = 1000)
    private String  password;

    private Boolean enabled;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    private  Date lastLoginDate;


    @ManyToMany
    private List<Role> roles;
}
