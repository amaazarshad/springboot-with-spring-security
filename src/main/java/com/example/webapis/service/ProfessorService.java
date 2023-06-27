package com.example.webapis.service;

import com.example.webapis.model.Department;
import com.example.webapis.repository.ProfessorRepository;
import com.example.webapis.model.Professor;
import com.example.webapis.model.ProfessorWithStudentAndDept;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {
    private final ProfessorRepository professorRepository;
    private final DepartmentService departmentService;
//    private final StudentService studentService;

    public ProfessorService(ProfessorRepository professorRepository, DepartmentService departmentService) {
        this.professorRepository = professorRepository;
        this.departmentService = departmentService;
    }

    public List<Professor> getProfessors() {
        return professorRepository.findAll();
    }

    public Optional<Professor> getProfessorById(int id) {
        return professorRepository.findById(id);
    }

    public ResponseEntity<String> addProfessor(ProfessorWithStudentAndDept professorWithStudentAndDept) {
        int deptId = professorWithStudentAndDept.getDeptId();
//        int[] studentIds = professorWithStudentAndDept.getStudentIds();
        String email = professorWithStudentAndDept.getEmail();
        String name = professorWithStudentAndDept.getName();

        Optional<Professor> professorByEmail = professorRepository.findProfessorByEmail(email);

        if (professorByEmail.isPresent()) {
            throw new IllegalArgumentException("Professor with this email already present.");
        }

        Professor professor = new Professor(name, email);

        if (deptId != 0) {
            Department department = departmentService.getDepartmentById(deptId)
                    .orElseThrow(() -> new IllegalArgumentException("Department with ID " + deptId + " not found."));
            professor.setDepartment(department);
        } else {
            throw new IllegalArgumentException("Department ID is required");
        }

//        if (studentIds != null) {
//            for (int id : studentIds) {
//                Optional<Student> student = studentService.getStudentById(id);
//                if (student.isPresent()) {
//                    professor.getStudents().add(student.get());
//                }
//            }
//        }

        professorRepository.save(professor);

        String message = "Professor added successfully";
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @Transactional
    public ResponseEntity<String> updateProfessor(ProfessorWithStudentAndDept professor, int id) {
        String name = professor.getName();
        String email = professor.getEmail();
        int deptId = professor.getDeptId();

        Professor existingProfessor = professorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Professor with ID " + id + " not found."));

        if (deptId != 0) {
            Department dept = departmentService.getDepartmentById(deptId)
                    .orElseThrow(() -> new IllegalArgumentException("Department with ID " + deptId + " not found."));
            existingProfessor.setDepartment(dept);
        }

        if (name != null && name.length() > 0) {
            existingProfessor.setName(name);
        }

        if (email != null && email.length() > 0) {
            Optional<Professor> professorWithEmail = professorRepository.findProfessorByEmail(email);
            if (professorWithEmail.isPresent()) {
                throw new IllegalArgumentException("Email already exist");
            }
            existingProfessor.setEmail(email);
        }

        String message = "Professor updated successfully";
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    public ResponseEntity<String> deleteProfessor(int id) {
        boolean exists = professorRepository.existsById(id);
        if (!exists) {
            throw new IllegalArgumentException("Professor with ID " + id + " not found.");
        }
        professorRepository.deleteById(id);
        String message = "Professor deleted successfully";
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
