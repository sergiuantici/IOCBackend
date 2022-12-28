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
@Table(name = "acord")
public class Acord {
    @EmbeddedId
    StudentTeacherId id;
    String documentUrl;
    LocalDateTime time;

}
