package br.com.orion.school.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.orion.school.model.Student;
import br.com.orion.school.repository.StudentRepository;

/**
 * StudentService
 */
@Service
public class StudentService  {

    @Autowired
    private StudentRepository studentRepository;

    public Student getById(Long id) {
        Optional<Student> student = studentRepository.findById(id);

        return student.orElse(null);

    }


}