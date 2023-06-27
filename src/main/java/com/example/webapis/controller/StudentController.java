package com.example.webapis.controller;

import com.example.webapis.model.Student;
import com.example.webapis.model.StudentDepartment;
import com.example.webapis.service.StudentService;
import com.example.webapis.model.StudentWithDeptAndProf;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("withDept")
    public List<StudentDepartment> getStudentsAndDepartments() {
        return studentService.getStudentsAndDepartments();
    }

    @GetMapping(path = "{id}")
    public Optional<Student> getStudentById(@PathVariable int id) {
        return studentService.getStudentById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<String> addStudent(@RequestBody StudentWithDeptAndProf student) {
        return studentService.addStudent(student);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<String> updateStudent(@RequestBody StudentWithDeptAndProf student, @PathVariable int id) {
        return studentService.updateStudent(student, id);
    }

    @PatchMapping("{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<String> patchStudent(@RequestBody StudentWithDeptAndProf student, @PathVariable int id) {
        return studentService.updateStudent(student, id);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {
        return studentService.deleteStudent(id);
    }
}
