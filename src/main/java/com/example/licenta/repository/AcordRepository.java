package com.example.licenta.repository;

import com.example.licenta.model.Acord;
import com.example.licenta.model.StudentTeacherId;
import com.example.licenta.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcordRepository extends JpaRepository<Acord, StudentTeacherId> {
}
