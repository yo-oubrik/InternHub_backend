//package ma.ensa.internHub.controllers;
//
//import java.util.List;
//import java.util.UUID;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import lombok.RequiredArgsConstructor;
//import ma.ensa.internHub.domain.dto.response.FlaggedStudentOverview;
//import ma.ensa.internHub.domain.dto.response.StudentFlagResponse;
//import ma.ensa.internHub.services.StudentFlagService;
//
//@RestController
//@RequestMapping("/api/v1/flagged-students")
//@RequiredArgsConstructor
//public class StudentFlagController {
//    private final StudentFlagService studentFlagService;
//
//    @GetMapping("/{id}/flags")
//    public List<StudentFlagResponse> getStudentFlagsHistory() {
//        return studentFlagService.getStudentFlagsHistory();
//    }
//
//    @GetMapping("/count")
//    public long countUnresolvedStudentFlags() {
//        return studentFlagService.countUnresolvedStudentFlags();
//    }
//
//    @GetMapping("/overview")
//    public List<FlaggedStudentOverview> getFlaggedStudentsOverview() {
//        return studentFlagService.getFlaggedStudentsOverview();
//    }
//
//    @GetMapping("/{id}/unresolved/count")
//    public long getUnresolvedStudentFlagsCountById(@PathVariable UUID id) {
//        return studentFlagService.getUnresolvedStudentFlagsCountById(id);
//    }
//
//    @GetMapping("/{id}/ignored/count")
//    public long getResolvedStudentFlagsCountById(@PathVariable UUID id) {
//        return studentFlagService.getIgnoredFlagsCountByStudentId(id);
//    }
//
//    @GetMapping("/{id}/warnings/count")
//    public long getStudentWarningsCount(@PathVariable UUID id) {
//        return studentFlagService.getStudentWarningsCount(id);
//    }
//}
