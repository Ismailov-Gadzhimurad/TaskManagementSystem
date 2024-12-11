package com.testapplication.desktop.services;


import com.testapplication.desktop.controllers.RequestController;
import com.testapplication.desktop.dto.TaskDTO;
import com.testapplication.desktop.models.Task;
import com.testapplication.desktop.repo.TaskRepository;
import com.testapplication.desktop.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {


    private static final Logger log = LoggerFactory.getLogger(RequestController.class);

    public final TaskRepository taskRepository;
    public final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public void postTask(TaskDTO taskDTO) {
        log.info("POST request received: {}", taskDTO);
        String title, description, status, priority, comment, author, executor;
        title = taskDTO.getTitle();
        description = taskDTO.getDescription();
        status = taskDTO.getStatus();
        priority = taskDTO.getPriority();
        comment = taskDTO.getComment();
        author = taskDTO.getAuthor();
        executor = taskDTO.getExecutor();

        Task task = new Task(title, description, status, priority, comment, author, executor);
        taskRepository.save(task);
        log.info(task.toString());
    }

    public void updateTask(long id, TaskDTO taskDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new  IllegalArgumentException("invalid task id:" + id));
        task.setTitle(taskDTO.getTitle());
        task.setTitle(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());
        task.setPriority(taskDTO.getPriority());
        task.setComment(taskDTO.getComment());
        task.setAuthor(taskDTO.getAuthor());
        task.setExecutor(taskDTO.getExecutor());
        taskRepository.save(task);
    }
}
