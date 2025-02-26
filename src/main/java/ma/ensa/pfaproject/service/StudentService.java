package ma.ensa.pfaproject.service;

import ma.ensa.pfaproject.entities.Student;

import java.util.List;

public interface StudentService {
    List<Student> getAllStudents();
    Student getStudentById(String id);
    Student saveStudent(Student student);
    void deleteStudent(String id);

    int getStudentCount();
}
