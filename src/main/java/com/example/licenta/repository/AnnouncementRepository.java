package com.example.licenta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.licenta.model.Announcement;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}
