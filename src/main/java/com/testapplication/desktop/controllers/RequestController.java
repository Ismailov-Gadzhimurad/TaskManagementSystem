package com.testapplication.desktop.controllers;


import com.testapplication.desktop.dto.TaskDTO;
import com.testapplication.desktop.dto.UserDTO;
import com.testapplication.desktop.models.MyUser;
import com.testapplication.desktop.models.Role;
import com.testapplication.desktop.models.Task;
import com.testapplication.desktop.repo.TaskRepository;
import com.testapplication.desktop.repo.UserRepository;
import com.testapplication.desktop.services.JwtService;
import com.testapplication.desktop.services.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class RequestController {

    private static final Logger log = LoggerFactory.getLogger(RequestController.class);

    public final TaskRepository taskRepository;
    public final UserRepository userRepository;
    public final TaskService taskService;

    @Autowired
    public RequestController(TaskRepository taskRepository, UserRepository userRepository, TaskService taskService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskService = taskService;
    }


    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> PostTaskRequest(@Validated @RequestBody TaskDTO taskDTO) {

        taskService.postTask(taskDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "POST request processed successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/read/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Task> getTaskRequest(@PathVariable("id") long id) {
        try {
            Optional<Task> optionalTask = taskRepository.findById(id);
            return optionalTask.map(task -> new ResponseEntity<>(task, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (DataAccessException ex) {

            log.error("Database error while fetching task: ", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteTaskRequest(@PathVariable("id") long id){

        try {
            Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Задача с ID " + id + " не найдена"));
            return new ResponseEntity<>("Задача удалена", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


    @PutMapping("update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Task> updateTaskRequest(@PathVariable("id") long id, @Validated @RequestBody TaskDTO taskDTO) {

        try {
            taskService.updateTask(id, taskDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("Error updating task: ", e);
            throw new RuntimeException(e);
        } catch (ResourceNotFoundException e) {
            log.error("Error updating task: ", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error updating task: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
