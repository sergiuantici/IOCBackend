package com.example.licenta.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class StudentTeacherId implements Serializable {
    private Long studentId;
    private Long teacherId;
}
