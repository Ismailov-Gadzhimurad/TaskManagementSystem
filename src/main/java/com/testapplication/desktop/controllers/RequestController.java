package com.testapplication.desktop.controllers;


import com.testapplication.desktop.dto.TaskDTO;
import com.testapplication.desktop.dto.UserDTO;
import com.testapplication.desktop.models.MyUser;
import com.testapplication.desktop.models.Task;
import com.testapplication.desktop.repo.TaskRepository;
import com.testapplication.desktop.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    public RequestController(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }


    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Object>> PostTask(@RequestBody TaskDTO taskDTO) {
        // Обработка тела запроса
        log.info("POST request received:");
        System.out.println(taskDTO);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "POST request processed successfully");


        Task task = new Task(taskDTO.getTitle(), taskDTO.getDescription(),taskDTO.getStatus(),taskDTO.getPriority(), taskDTO.getComment(), taskDTO.getAuthor(), taskDTO.getExecutor());
        taskRepository.save(task);
        log.info(task.toString());
        return new ResponseEntity<>(response, HttpStatus.OK);







    }

    @GetMapping("/read/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Task> getTask(@PathVariable("id") long id) {
        try {
            Optional<Task> optionalTask = taskRepository.findById(id);
            if (optionalTask.isPresent()) {
                return new ResponseEntity<>(optionalTask.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException ex) {

            log.error("Database error while fetching task: ", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteTask(@PathVariable("id") long id){

        try {
            Task task = taskRepository.findById(id)
                    .orElseThrow(() -> new  IllegalArgumentException("invalid task id:" + id));

            return new ResponseEntity<>("Задача удалена", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


    @PutMapping("update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Task> updateTask(@PathVariable("id") long id, @Validated @RequestBody TaskDTO taskDTO) {




        try {
            Task task = taskRepository.findById(id)
                    .orElseThrow(() -> new  IllegalArgumentException("invalid task id:" + id));

            task.setTitle(taskDTO.getTitle());

            task.setTitle(taskDTO.getDescription());
            task.setStatus(taskDTO.getStatus());
            task.setPriority(taskDTO.getPriority());
            task.setComment(taskDTO.getComment());
            task.setAuthor(taskDTO.getAuthor());
            task.setExecutor(taskDTO.getExecutor());




            Task updatedTask = taskRepository.save(task);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);

        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Error updating task: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PostMapping("/new-user")
    public ResponseEntity<Map<String, Object>> addUser(@Validated @RequestBody UserDTO UserDTO) {
        try {
            log.info(String.valueOf(UserDTO));
            MyUser user = new MyUser(UserDTO.getUsername(), UserDTO.getPassword(), UserDTO.getRoles());
            System.out.println(user);
            userRepository.save(user);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "POST request processed successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            log.error("error", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);        }

    }




}
