package ma.ensa.internHub.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.request.StudentRequest;
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

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable UUID id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<StudentResponse> getStudentByEmail(@PathVariable String email) {
        return ResponseEntity.ok(studentService.getStudentByEmail(email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> updateStudent(@PathVariable UUID id, @RequestBody StudentRequest request) {
        return ResponseEntity.ok(studentService.updateStudentById(id, request));
    }

    @GetMapping("/count-by-month")
    public Map<String, Long> countStudentsByMonth() {
        return studentService.countStudentsByMonth();
    }
}