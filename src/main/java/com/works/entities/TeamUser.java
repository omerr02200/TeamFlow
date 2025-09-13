package com.works.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class TeamUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tuid;
    @NotNull
    @ManyToOne
    @JoinColumn
    private Team team;
    @NotNull
    @ManyToOne
    @JoinColumn(unique = true)
    private User user;
}
