package com.works.services;

import com.works.entities.Role;
import com.works.entities.Task;
import com.works.entities.TeamUser;
import com.works.entities.dto.TaskUpdateDto;
import com.works.repositories.TaskRepository;
import com.works.repositories.TeamRepository;
import com.works.repositories.TeamUserRepository;
import com.works.utils.ETaskPriority;
import com.works.utils.ETaskStatus;
import com.works.utils.Messages;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    private final HttpServletRequest request;
    private final UserService userService;

    private final ModelMapper modelMapper;

    private final TeamRepository teamRepository;
    private final TeamUserRepository teamUserRepository;

    public ResponseEntity save(Task task) {
//        if(userService.isAdmin()){
//            task.setCreated(new Date());
//            return new ResponseEntity(taskRepository.save(task), HttpStatus.CREATED);
//        } else
//            return new ResponseEntity(Messages.forBidden, HttpStatus.FORBIDDEN);

        task.setCreated(new Date());
        return new ResponseEntity(taskRepository.save(task), HttpStatus.CREATED);
    }


    public ResponseEntity update(TaskUpdateDto taskUpdateDto) {

//        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
//
//        Task taskUpdated = modelMapper.map(taskUpdateDto, Task.class);
//
//        Task task = taskRepository.findById(taskUpdated.getTid()).orElseThrow(() ->
//                new IllegalArgumentException("Görev bulunamadı"));
//
//        long uid = userService.user().getUid();
//        List<Task> tasks = taskRepository.findByAssignee_UidEquals(uid);
//
//        if(userService.isAdmin()) {
//            task.setStatus(taskUpdated.getStatus());
//            taskRepository.save(task);
//            responseEntity = new ResponseEntity(task, HttpStatus.OK);
//        }else {
//            for (Task t : tasks) {
//                if(t.getTid() == task.getTid())
//                    task.setStatus(taskUpdated.getStatus());
//                taskRepository.save(task);
//                responseEntity = new ResponseEntity(task, HttpStatus.OK);
//            }
//        }
//
//        return responseEntity;

        Optional<Task> updatedTask = taskRepository.findById(taskUpdateDto.getTid());
        if(updatedTask.isPresent()) {
            Task task = updatedTask.get();
            task.setStatus(taskUpdateDto.getStatus());
            return new ResponseEntity(taskRepository.save(task), HttpStatus.OK);
        }
        return new ResponseEntity(Messages.taskNotFound, HttpStatus.NOT_FOUND);
    }

    public Page<Task> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        if(userService.isAdmin()) {
            return taskRepository.findAll(pageable);
        } else {
            long uid = userService.user().getUid();
            List<Task> tasks = taskRepository.findByAssignee_UidEquals(uid);
            return taskRepository.findAll(pageable);
        }
    }

    public Page<Task> findByStatus(ETaskStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskRepository.findByStatusEquals(status, pageable);
    }

    public Page<Task> findByPriority(ETaskPriority priority, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskRepository.findByPriorityEquals(priority, pageable);
    }

    public List<Task> findByAssignee_UidEquals(Long uid) {
        return taskRepository.findByAssignee_UidEquals(uid);
    }

    public ResponseEntity singleTask(Long tid) {
        Optional<Task> task = taskRepository.findById(tid);
        if (task.isPresent()) {
            return new ResponseEntity(task.get(), HttpStatus.OK);
        }
        //String msg = "Böyle bir görev yok";
        return new ResponseEntity(Messages.taskNotFound, HttpStatus.BAD_REQUEST);
    }

    //admin olmayan silemez
    public ResponseEntity delete (Long tid) {
        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);

        if(tid == null || tid < 1 ) {
            responseEntity = new ResponseEntity("Lütfen pozitif bir tam sayı giriniz", HttpStatus.BAD_REQUEST);
        } else {
            if (taskRepository.findById(tid).isPresent()) {
                taskRepository.deleteById(tid);
                responseEntity = new ResponseEntity(Messages.deletedTask, HttpStatus.OK);
            }
            responseEntity = new ResponseEntity(Messages.taskNotFound, HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }
}
