package com.works.entities.dto;

import com.works.entities.Task;
import com.works.utils.ETaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Task}
 */
@Value
public class TaskUpdateDto implements Serializable {
    @NotNull
    Long tid;
    @NotNull
    ETaskStatus status;
}