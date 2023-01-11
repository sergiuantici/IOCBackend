package com.example.licenta.repository;

import com.example.licenta.model.AdminAnnouncement;
import com.example.licenta.model.AdminAnnouncementType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminAnnouncementRepository extends JpaRepository<AdminAnnouncement, Long> {

    List<AdminAnnouncement> findAllByType(AdminAnnouncementType type);
}