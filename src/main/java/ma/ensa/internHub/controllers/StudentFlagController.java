package ma.ensa.internHub.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ma.ensa.internHub.domain.dto.response.FlaggedStudentOverview;
import ma.ensa.internHub.services.StudentFlagService;

@RestController
@RequestMapping("/api/v1/flagged-students")
@RequiredArgsConstructor
public class StudentFlagController {
    private final StudentFlagService studentFlagService;

    @GetMapping("/count")
    public long countUnresolvedStudentFlags() {
        return studentFlagService.countUnresolvedStudentFlags();
    }

    @GetMapping("/overview")
    public List<FlaggedStudentOverview> getFlaggedStudentsOverview() {
        return studentFlagService.getFlaggedStudentsOverview();
    }

}
