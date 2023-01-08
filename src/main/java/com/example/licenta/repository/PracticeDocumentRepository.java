package com.example.licenta.repository;

import com.example.licenta.model.PracticeDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PracticeDocumentRepository extends JpaRepository <PracticeDocument, Long> {
}
