package com.example.licenta.repository;

import com.example.licenta.model.StudentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentDetails,Long> {
	StudentDetails findByUserId(Long studentId);
}
