package com.example.licenta.repository;

import com.example.licenta.model.Coordonare;
import com.example.licenta.model.StudentTeacherId;
import com.example.licenta.model.TeacherDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CoordonationRepository extends JpaRepository<Coordonare, StudentTeacherId> {
    @Query("select case when count(c) > 0 then true else false end from Coordonare c where c.id.studentId = :studentId")
    Boolean existsByStudentId(@Param("studentId") Long studentId);

    @Query("select c.id.teacherId from Coordonare c where c.id.studentId = :studentId")
    Long findTeacherIdByStudentId(@Param("studentId") Long studentId);

    Coordonare findFirstById_StudentId(Long studentId);

    @Query("select t from Coordonare t where t.id.teacherId = :teacherId")
    List<Coordonare> findByTeacherId(@Param("teacherId") Long teacherId);
}
