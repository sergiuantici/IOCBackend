package com.example.licenta.repository;

import com.example.licenta.model.Coordonare;
import com.example.licenta.model.StudentTeacherId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoordonationRepository extends JpaRepository<Coordonare, StudentTeacherId> {
}
