package com.likelion.seminar.student.controller;

import com.likelion.seminar.student.dto.StudentDTO;
import com.likelion.seminar.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public void createStudent(@RequestBody StudentDTO studentDTO) {
        studentService.createStudent(studentDTO);
    }

    @GetMapping
    public List<StudentDTO> getStudents(){
        return studentService.getStudents();
    }

    @GetMapping("/{studentId}")
    public StudentDTO getStudent(@PathVariable String studentId){
        return studentService.getStudent(studentId);
    }

    @PutMapping("/{studentId}")
    public void updateStudent(@PathVariable String studentId, @RequestBody StudentDTO studentDTO){
        studentService.updateStudent(studentId, studentDTO);
    }
}
