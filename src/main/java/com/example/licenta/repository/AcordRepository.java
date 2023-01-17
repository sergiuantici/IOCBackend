package com.example.licenta.repository;

import com.example.licenta.model.Acord;
import com.example.licenta.model.StudentTeacherId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AcordRepository extends JpaRepository<Acord, StudentTeacherId> {

    @Query("select new Acord(e.id,e.documentUrl,e.time) " +
            "from Acord e where e.id.teacherId=:teacherId")
    List<Acord> findAllByTeacherId(@Param("teacherId") Long teacherId);

    @Query("select new Acord(e.id,e.documentUrl,e.time) " +
            "from Acord e where e.id.studentId=:studentId")
    List<Acord> findAllById_StudentId(@Param("studentId") Long studentId);
}
