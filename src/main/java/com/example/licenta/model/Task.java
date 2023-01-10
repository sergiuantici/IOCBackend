package com.example.licenta.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task")
public class Task {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//aici am schimbat pentru ca daca lasam id-ul de jos atunci se putea dadea un singur task de la un coordonator pt un student (banuiesc ca
    // un student va avea mai multe taskuri)
    StudentTeacherId studentTeacherid;

    String message;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm")
    LocalDateTime deadline;

    @ElementCollection
    @CollectionTable(name = "task_turn_ins",joinColumns = @JoinColumn(name = "id"))
    @Column(name = "document_list")
    List<String> documentUrls;

    Double grade;

    public Task(StudentTeacherId id) {
        this.studentTeacherid = id;
    }
}
