package com.likelion.seminar.student.service;

import com.likelion.seminar.student.dto.StudentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final List<StudentDTO> studentDTOList;

    public void createStudent(StudentDTO studentDTO){
        this.studentDTOList.add(studentDTO);
    }
    public List<StudentDTO> getStudents(){
        return this.studentDTOList;
    }
}
