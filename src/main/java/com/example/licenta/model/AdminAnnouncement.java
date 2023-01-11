package com.example.licenta.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admin_anouncement")
public class AdminAnnouncement {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String message;
    @Column
    private Date created = new Date();
    @Column
    private AdminAnnouncementType type;

    public AdminAnnouncement(String message, AdminAnnouncementType type) {
        this.message = message;
        this.type = type;
    }
}
