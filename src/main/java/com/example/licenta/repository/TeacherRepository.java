package com.example.licenta.repository;

import com.example.licenta.model.TeacherDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeacherRepository extends JpaRepository<TeacherDetails,Long> {
    @Query("select t from TeacherDetails t where t.user.id = :teacherId")
    TeacherDetails findByUserId(@Param("teacherId") Long teacherId);
}
