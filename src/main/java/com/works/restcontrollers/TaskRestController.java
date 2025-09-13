package com.works.restcontrollers;

import com.works.entities.Task;
import com.works.entities.dto.TaskUpdateDto;
import com.works.services.TaskService;
import com.works.utils.ETaskPriority;
import com.works.utils.ETaskStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("task")
@Validated
public class TaskRestController {
    private final TaskService taskService;

    @PostMapping("save")
    @PreAuthorize("hasRole('admin') or hasRole('manager') and @teamSecurity.isTeamManager(#task.getAssignee().getUid())")
    public ResponseEntity save(@Valid @RequestBody Task task) {
        return taskService.save(task);
    }

//    @PutMapping("update")
//    public ResponseEntity update(@Valid @RequestBody Task task) {
//        return taskService.update(task);
//    }

    @PutMapping("update")
    @PreAuthorize("hasRole('admin') or (hasRole('member') and @taskSecurity.isAssignedToUser(#task.tid)) ")
    public ResponseEntity update(@Valid @RequestBody TaskUpdateDto task) {
        return taskService.update(task);
    }

    @GetMapping("list")
    public Page<Task> list(@RequestParam(defaultValue = "0")  int page,
                           @Min(5) @Max(10) @RequestParam(defaultValue = "10") int size) {
        return taskService.findAll(page, size);
    }

    @GetMapping("findByStatus")
    public Page<Task> findByStatus(@RequestParam ETaskStatus status,
                                   @RequestParam(defaultValue = "0")  int page,
                                   @RequestParam(defaultValue = "10") int size) {
        return taskService.findByStatus(status, page, size);

    }

    @GetMapping("findByPriority")
    public Page<Task> findByPriority(@RequestParam ETaskPriority priority,
                                   @RequestParam(defaultValue = "0")  int page,
                                   @RequestParam(defaultValue = "10") int size) {
        return taskService.findByPriority(priority, page, size);

    }

    @GetMapping("findByAssignee")
    public List<Task> findByAssignee(@RequestParam Long uid) {
        return taskService.findByAssignee_UidEquals(uid);
    }

    @GetMapping("singleTask")
    public ResponseEntity singleTask(@Min(1) @RequestParam(defaultValue = "1") Long tid) {
        return taskService.singleTask(tid);
    }

    @DeleteMapping("delete/{tid}")
    public ResponseEntity delete(@PathVariable Long tid) {
        return taskService.delete(tid);
    }

}
