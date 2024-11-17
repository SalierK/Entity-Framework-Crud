package com.codecraft.Crud_app.repository;

import com.codecraft.Crud_app.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Long> {
}
