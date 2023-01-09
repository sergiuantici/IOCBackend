package com.example.licenta.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task")
public class Task {
    @EmbeddedId
    StudentTeacherId id;

    String message;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm")
    LocalDateTime deadline;

    String documentUrl;

    Double grade;

    public Task(StudentTeacherId id) {
        this.id = id;
    }
}
