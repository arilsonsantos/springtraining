package br.com.orion.cursospring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.orion.cursospring.model.Student;

/**
 * StudentRepository
 */
public interface StudentRepository extends CrudRepository<Student, Long> {

    List<Student> findByNameIgnoreCaseContaining(String name);
    
}