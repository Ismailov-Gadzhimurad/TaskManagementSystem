package com.testapplication.desktop.repo;

import com.testapplication.desktop.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends JpaRepository<Task,Long> {
}
