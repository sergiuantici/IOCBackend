package com.example.licenta.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class StudentTeacherId implements Serializable {
    private Long studentId;
    private Long teacherId;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public StudentTeacherId(Long studentId, Long teacherId) {
        this.studentId=studentId;
        this.teacherId=teacherId;
    }

    public StudentTeacherId() {

    }
}
