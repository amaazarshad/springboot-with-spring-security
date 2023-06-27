package com.example.webapis.model;

import java.time.LocalDate;
import java.util.List;

public class StudentWithDeptAndProf {
    private String name;
    private String email;
    private LocalDate dob;
    private int deptId;
    private int[] profIds;

    public StudentWithDeptAndProf(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public int[] getProfIds() {
        return profIds;
    }

    public void setProfIds(int[] profIds) {
        this.profIds = profIds;
    }
}
