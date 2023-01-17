package com.example.licenta.repository;

import com.example.licenta.model.GlobalDetails;
import com.example.licenta.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
    @Query("Select m from Message m where (m.fromId = :studentId and m.toId = :teacherId) or (m.fromId = :teacherId and m.toId = :studentId) order by m.time")
    List<Message> findMessagesForStudentAndTeacher(@Param("studentId") Long studentId, @Param("teacherId") Long teacherId);
}
