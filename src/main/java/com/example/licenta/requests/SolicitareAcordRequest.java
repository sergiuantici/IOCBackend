package com.example.licenta.requests;

public class SolicitareAcordRequest {
    private long studentId;
    private long teacherId;
    private String fileURL;

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }

    public SolicitareAcordRequest(long studentId, long teacherId, String fileURL) {
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.fileURL = fileURL;
    }
}
