package com.example.licenta.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "globalDetails")
public class GlobalDetails {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    Long practiceHoursTotal;
    LocalDate practiceEndDate;
    String rules;
    String evaluationCriteria;
}
