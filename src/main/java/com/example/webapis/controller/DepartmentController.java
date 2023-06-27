package com.example.webapis.controller;

import com.example.webapis.model.Department;
import com.example.webapis.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/dept")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public List<Department> getDepartments() {
        return departmentService.getDepartments();
    }

    @GetMapping("{id}")
    public Optional<Department> getDepartmentById(@PathVariable int id) {
        return departmentService.getDepartmentById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<String> addDepartment(@RequestBody Department dept) {
        return departmentService.addDepartment(dept);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<String> updateStudent(@RequestBody Department dept, @PathVariable int id) {
        return departmentService.updateDepartment(dept, id);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<String> deleteDepartment(@PathVariable int id) {
        return departmentService.deleteDepartment(id);
    }
}
