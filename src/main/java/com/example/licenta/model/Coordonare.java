package com.example.licenta.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coordonare")
public class Coordonare {
    @EmbeddedId
    private StudentTeacherId id;
}
