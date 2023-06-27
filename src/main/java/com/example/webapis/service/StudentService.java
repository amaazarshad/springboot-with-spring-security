package com.example.webapis.service;

import com.example.webapis.model.GenericMessage;
import com.example.webapis.model.*;
import com.example.webapis.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    //    private final List<Student> students;
    private final StudentRepository studentRepository;
    private final DepartmentService departmentService;
    private final ProfessorService professorService;

    @Autowired
    public StudentService(StudentRepository studentRepository, DepartmentService departmentService, ProfessorService professorService) {
        this.studentRepository = studentRepository;
        this.departmentService = departmentService;
        this.professorService = professorService;
//        students = new ArrayList<>();
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public List<StudentDepartment> getStudentsAndDepartments() {
        return studentRepository.getStudentDepartmentInfo();
//        List<StudentDepartment> stdDept = new ArrayList<>();
//        List<Student> students = studentRepository.findAll();
//        students.forEach(student -> {
//            stdDept.add(new StudentDepartment(
//                    student.getName(),
//                    student.getDepartment().getName()
//            ));
//        });
//        return stdDept;
    }

    public Optional<Student> getStudentById(int id) {
        return studentRepository.findById(id);
    }

    public ResponseEntity<String> addStudent(StudentWithDeptAndProf studentWithDeptAndProf) {
        int deptId = studentWithDeptAndProf.getDeptId();
        int[] profIds = studentWithDeptAndProf.getProfIds();
        String email = studentWithDeptAndProf.getEmail();
        String name = studentWithDeptAndProf.getName();
        LocalDate dob = studentWithDeptAndProf.getDob();

        Optional<Student> studentByEmail = studentRepository.findStudentsByEmail(email);

        if (studentByEmail.isPresent()) {
            throw new IllegalArgumentException("Student with this email already present.");
        }

        Student student = new Student(name, email, dob);

        if (deptId != 0) {
            Department department = departmentService.getDepartmentById(deptId)
                    .orElseThrow(() -> new IllegalArgumentException("Department with ID " + deptId + " not found."));
            student.setDepartment(department);
        } else {
            throw new IllegalArgumentException("Department ID is required");
        }

        if (profIds != null) {
            for (int id : profIds) {
                Optional<Professor> professor = professorService.getProfessorById(id);
                if (professor.isPresent()) {
                    student.getProfessors().add(professor.get());
                }
            }
        }

        studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body("Student added successfully");
    }

    @Transactional
    public ResponseEntity<String> updateStudent(StudentWithDeptAndProf student, int id) {
        String name = student.getName();
        String email = student.getEmail();
        int deptId = student.getDeptId();
        int[] profIds = student.getProfIds();

        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student with ID " + id + " not found."));

        if (deptId != 0) {
            Department dept = departmentService.getDepartmentById(deptId)
                    .orElseThrow(() -> new IllegalArgumentException("Department with ID " + deptId + " not found."));
            existingStudent.setDepartment(dept);
        }

        if (profIds != null) {
            existingStudent.setProfessors(new ArrayList<>());
            for (int profId : profIds) {
                Optional<Professor> professor = professorService.getProfessorById(profId);
                if (professor.isPresent()) {
                    existingStudent.getProfessors().add(professor.get());
                }
            }
        }

        if (name != null && name.length() > 0) {
            existingStudent.setName(name);
        }

        if (email != null && email.length() > 0) {
            Optional<Student> studentWithEmail = studentRepository.findStudentsByEmail(email);
            if (studentWithEmail.isPresent()) {
                throw new IllegalArgumentException("Email already exist");
            }
            existingStudent.setEmail(email);
        }

        return ResponseEntity.status(HttpStatus.OK).body("Student updated successfully");
    }

    public ResponseEntity<String> deleteStudent(int id) {
        boolean exists = studentRepository.existsById(id);
        if (!exists) {
            throw new IllegalArgumentException("Student with ID " + id + " not found.");
        }
        studentRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Student deleted successfully");
    }

    // **************** these methods are for manipulating data without db *****************
//    public List<Student> getStudentsWithoutDB() {
//        return students;
//    }
//
//    public Student getStudentByIdWithoutDB(int id) {
//        for (Student student : students) {
//            if (student.getId() == id) {
//                return student;
//            }
//        }
//        throw new IllegalArgumentException("Student with ID " + id + " not found.");
//    }
//
//    public List<Student> addStudentWithoutDB(Student student) {
//        if (student.getId() <= 0) {
//            throw new IllegalArgumentException("Invalid student ID: " + student.getId());
//        }
//        boolean idExists = students.stream().anyMatch(s -> s.getId() == student.getId());
//        if (idExists) {
//            throw new IllegalArgumentException("Student ID already exists: " + student.getId());
//        }
//        students.add(student);
//        return students;
//    }
//
//    public List<Student> deleteStudentWithoutDB(int id) {
//        boolean removed = students.removeIf(student -> student.getId() == id);
//        if (!removed) {
//            throw new IllegalArgumentException("Student with ID " + id + " not found.");
//        }
//        return students;
//    }
}
