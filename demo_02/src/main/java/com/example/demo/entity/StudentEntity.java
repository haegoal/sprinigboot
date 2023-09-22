package com.example.demo.entity;


import com.example.demo.dto.StudentDTO;
import lombok.*;

import javax.persistence.*;

@Setter(AccessLevel.PRIVATE)
@Getter
@ToString
@Entity
@Table(name = "student_table")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false, unique = true)
    private String studentNumber;

    @Column(length = 20, nullable = false)
    private String studentName;

    @Column(length = 30, nullable = false)
    private String studentMobile;

    @Column(length = 50, nullable = false)
    private String studentMajor;


    public static StudentEntity toSaveEntity(StudentDTO studentDTO){
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setStudentNumber(studentDTO.getStudentNumber());
        studentEntity.setStudentName(studentDTO.getStudentName());
        studentEntity.setStudentMobile(studentDTO.getStudentMobile());
        studentEntity.setStudentMajor(studentDTO.getStudentMajor());
        return studentEntity;
    }

    public static StudentEntity toUpdateEntity(StudentDTO studentDTO){
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(studentDTO.getId());
        studentEntity.setStudentNumber(studentDTO.getStudentNumber());
        studentEntity.setStudentName(studentDTO.getStudentName());
        studentEntity.setStudentMobile(studentDTO.getStudentMobile());
        studentEntity.setStudentMajor(studentDTO.getStudentMajor());
        return studentEntity;
    }
}
