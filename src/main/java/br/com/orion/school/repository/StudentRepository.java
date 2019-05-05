package br.com.orion.school.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.orion.school.model.Student;

/**
 * StudentRepository
 */
public interface StudentRepository extends  PagingAndSortingRepository<Student, Long> {

    List<Student> findByNameIgnoreCaseContaining(String name);
    
}