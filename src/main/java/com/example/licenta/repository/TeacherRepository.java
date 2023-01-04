package com.example.licenta.repository;

import com.example.licenta.model.TeacherDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<TeacherDetails,Long> {
}
