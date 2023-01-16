package com.example.licenta.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "message")
public class Message {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long fromId,toId;
    String text;
    LocalDateTime time;

    public Message(Long fromId, Long toId, String text, LocalDateTime time) {
        this.fromId = fromId;
        this.toId = toId;
        this.text = text;
        this.time = time;
    }
}
