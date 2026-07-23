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

    public StudentDTO getStudent(String studentId) {
        return this.studentDTOList.stream().filter(s-> s.getStudentId().equals(studentId)).findFirst().orElse(null);
    }

    public void updateStudent(String studentId, StudentDTO studentDTO) {
        StudentDTO target = this.studentDTOList.stream().filter(s->s.getStudentId().equals(studentId)).findFirst().orElse(null);
        if(target == null){
            throw new IllegalArgumentException("해당 학번 학생이 존재하지 않습니다.");
        }else {
            if (studentDTO.getName() != null) {
                target.setName(studentDTO.getName());
            }
            if (studentDTO.getDateOfBirth() != null) {
                target.setDateOfBirth(studentDTO.getDateOfBirth());
            }
        }
    }
}
