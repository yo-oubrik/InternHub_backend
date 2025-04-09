package ma.ensa.internHub.controllers;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.response.StudentResponse;
import ma.ensa.internHub.services.StudentService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/count")
    public long getTotalStudents() {
        return studentService.countStudents();
    }

    @GetMapping
    public List<StudentResponse> getAllStudents() {
        return studentService.getAllStudents();
    }

    @DeleteMapping("/{id}")
    public void deleteStudentById(@PathVariable UUID id) {
        studentService.deleteStudentById(id);
    }

    @GetMapping("/count-by-month")
    public Map<String, Long> countStudentsByMonth() {
        return studentService.countStudentsByMonth();
    }
}