package com.example.licenta.repository;

import com.example.licenta.model.StudentTeacherId;
import com.example.licenta.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, StudentTeacherId> {
}
