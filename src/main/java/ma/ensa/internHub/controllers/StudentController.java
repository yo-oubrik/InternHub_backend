package ma.ensa.internHub.controllers;

import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.request.StudentUpdateRequest;
import ma.ensa.internHub.domain.dto.response.StudentResponse;
import ma.ensa.internHub.services.StudentService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ma.ensa.internHub.domain.dto.request.NotificationRequest;

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
    public ResponseEntity<StudentResponse> updateStudent(@PathVariable UUID id,
            @RequestBody StudentUpdateRequest request) {
        return ResponseEntity.ok(studentService.updateStudentById(id, request));
    }

    @GetMapping("/count-by-month")
    public Map<String, Long> countStudentsByMonth() {
        return studentService.countStudentsByMonth();
    }

    @PostMapping("/{id}/block")
    public ResponseEntity<Void> blockStudent(
            @PathVariable UUID id,
            @ModelAttribute NotificationRequest request) {
        studentService.blockStudent(id, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/unblock")
    public ResponseEntity<Void> unblockStudent(
            @PathVariable UUID id,
            @ModelAttribute NotificationRequest request) {
        studentService.unblockStudent(id, request);
        return ResponseEntity.ok().build();
    }

}