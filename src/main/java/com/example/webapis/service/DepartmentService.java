package com.example.webapis.service;


import com.example.webapis.repository.DepartmentRepository;
import com.example.webapis.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    public Optional<Department> getDepartmentById(int id) {
        return departmentRepository.findById(id);
    }

    public ResponseEntity<String> addDepartment(Department dept) {
        Optional<Department> department=departmentRepository.findDepartmentByName(dept.getName());
        if(department.isPresent()){
            throw new IllegalArgumentException("Department already exist");
        }
        departmentRepository.save(dept);
        String message = "Department added successfully";
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @Transactional
    public ResponseEntity<String> updateDepartment(Department dept, int id) {
        String name = dept.getName();

        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department with ID " + id + " not found."));

        if (name != null && name.length() > 0) {
            Optional<Department> department = departmentRepository.findDepartmentByName(name);
            if (department.isPresent()) {
                throw new IllegalArgumentException("Department already exist");
            }
            existingDepartment.setName(name);
        }

        String message = "Department updated successfully";
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    public ResponseEntity<String> deleteDepartment(int id) {
        boolean exists = departmentRepository.existsById(id);
        if (!exists) {
            throw new IllegalArgumentException("Department with ID " + id + " not found.");
        }
        departmentRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Department deleted successfully");
    }
}
