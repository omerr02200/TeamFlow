package com.works.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tid;
    @Size(min = 5, max = 20)
    @NotEmpty
    @NotNull
    private String  name;
    @Size(min = 20, max = 100)
    @NotEmpty
    @NotNull
    private String  description;
}
