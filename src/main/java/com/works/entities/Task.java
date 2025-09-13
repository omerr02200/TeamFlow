package com.works.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.works.utils.ETaskPriority;
import com.works.utils.ETaskStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tid;
    private String title;
    private String description;
    @Enumerated
    private ETaskStatus status;
    @Enumerated
    private ETaskPriority priority;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private Date dueDate;
    @ManyToOne
    private User assignee;
}
