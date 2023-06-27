package com.example.webapis.controller;

import com.example.webapis.model.Professor;
import com.example.webapis.service.ProfessorService;
import com.example.webapis.model.ProfessorWithStudentAndDept;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/professor")
public class ProfessorController {
    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping
    public List<Professor> getProfessors() {
        return professorService.getProfessors();
    }

    @GetMapping("{id}")
    public Optional<Professor> getProfessorById(@PathVariable int id) {
        return professorService.getProfessorById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<String> addProfessor(@RequestBody ProfessorWithStudentAndDept professor) {
        return professorService.addProfessor(professor);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<String> updateProfessor(@RequestBody ProfessorWithStudentAndDept professor, @PathVariable int id) {
        return professorService.updateProfessor(professor, id);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<String> deleteProfessor(@PathVariable int id) {
        return professorService.deleteProfessor(id);
    }
}
