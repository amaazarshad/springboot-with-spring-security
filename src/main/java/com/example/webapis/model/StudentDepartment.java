package com.example.webapis.model;

public class StudentDepartment {
    private String studentName;
    private String departmentName;

    public StudentDepartment(){}

    public StudentDepartment(String studentName, String departmentName) {
        this.studentName = studentName;
        this.departmentName = departmentName;
    }

    // Getters and setters

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
