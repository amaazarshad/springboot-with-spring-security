package com.example.webapis.repository;

import com.example.webapis.model.Student;
import com.example.webapis.model.StudentDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//interface StudentDepartmentProjection {
//    String getStudentName();
//    String getDepartmentName();
//}

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
    Optional<Student> findStudentsByEmail(String email);

//        @Query(value = "SELECT s.name AS studentName, d.name AS departmentName FROM student s " +
//            "INNER JOIN department d ON s.department_id = d.id", nativeQuery = true)
    @Query("SELECT new com.example.webapis.model.StudentDepartment(s.name, d.name) FROM Student s INNER JOIN s.department d")
    List<StudentDepartment> getStudentDepartmentInfo();
}
