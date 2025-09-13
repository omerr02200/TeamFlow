package com.works.configs;

import com.works.entities.Task;
import com.works.entities.TeamUser;
import com.works.repositories.TaskRepository;
import com.works.repositories.TeamUserRepository;
import com.works.repositories.UserRepository;
import com.works.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("taskSecurity")
@RequiredArgsConstructor
public class TaskSecurity {

    private final UserService userService;
    private final TaskRepository taskRepository;

    private final TeamUserRepository teamUserRepository;

    public boolean isAssignedToUser(Long taskId) {

        long uid = userService.user().getUid();
        List<Task> tasks = taskRepository.findByAssignee_UidEquals(uid);

        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new IllegalArgumentException("Görev bulunamadı"));

        for (Task t : tasks) {
            if(t.getTid() == task.getTid()) {
                return true;
            }
        }
        return false;
    }

}
