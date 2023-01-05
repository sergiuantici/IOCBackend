package com.example.licenta.model;

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
@Table(name = "solicitareAcord")

public class SoliciareAcord {
    @EmbeddedId
    StudentTeacherId id;
    String documentUrl;
    LocalDateTime time;

    public SoliciareAcord(StudentTeacherId studentTeacherId) {
        id=studentTeacherId;
    }
}
