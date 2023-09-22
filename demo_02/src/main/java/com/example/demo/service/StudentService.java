package com.example.demo.service;

import com.example.demo.dto.StudentDTO;
import com.example.demo.entity.StudentEntity;
import com.example.demo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;


    public Long save(StudentDTO studentDTO) {
        System.out.println("studentDTO = " + studentDTO);
        // DTO -> Entity 변환을 위한 메서드 호출
        StudentEntity studentEntity = StudentEntity.toSaveEntity(studentDTO);
        Long savedId = studentRepository.save(studentEntity).getId();
        return savedId;
    }

    public List<StudentDTO> findAll() {
        List<StudentEntity> studentEntityList = studentRepository.findAll();
        List<StudentDTO> studentDTOList = new ArrayList<>();
        for (StudentEntity studentEntity: studentEntityList) {
            studentDTOList.add(StudentDTO.toDTO(studentEntity));
        }
        return studentDTOList;

    }


    public StudentDTO findById(Long id) {
//        Optional<StudentEntity> optionalStudentEntity = studentRepository.findById(id);
//        if(optionalStudentEntity.isPresent()){
//            StudentEntity studentEntity = optionalStudentEntity.get();
//            return StudentDTO.toDTO(studentEntity);
//        }else{
//            return null;
//        }
        StudentEntity studentEntity = studentRepository.findById(id).orElseThrow(()-> new NoSuchElementException());
        return StudentDTO.toDTO(studentEntity);
    }

    public void update(StudentDTO studentDTO) {
        StudentEntity studentEntity = StudentEntity.toUpdateEntity(studentDTO);
        studentRepository.save(studentEntity);
    }

    public void delete(Long id) {
        studentRepository.deleteById(id);
    }
}
