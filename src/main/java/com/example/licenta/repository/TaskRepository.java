package com.example.licenta.repository;

import com.example.licenta.model.StudentTeacherId;
import com.example.licenta.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
	List<Task> findAllByStudentTeacherid(StudentTeacherId id);
	@Query("select t from Task t where t.id = :teacherId " +
			"and t.documentUrls is not empty")
	List<Task> findAllByTeacherIdWithDocument(@Param("teacherId")Long teacherId);
}
