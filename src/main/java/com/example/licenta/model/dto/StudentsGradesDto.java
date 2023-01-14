package com.example.licenta.model.dto;

public class StudentsGradesDto {
    private Double normalGrade;
    private Double reTakeGrade;
    private String firstName;
    private String lastName;

    public StudentsGradesDto(Double normalGrade, Double reTakeGrade, String firstName, String lastName) {
        this.normalGrade = normalGrade;
        this.reTakeGrade = reTakeGrade;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Double getNormalGrade() {
        return normalGrade;
    }

    public void setNormalGrade(Double normalGrade) {
        this.normalGrade = normalGrade;
    }

    public Double getReTakeGrade() {
        return reTakeGrade;
    }

    public void setReTakeGrade(Double reTakeGrade) {
        this.reTakeGrade = reTakeGrade;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
