package br.com.orion.cursospring.endpoint;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.orion.cursospring.error.CustomErrorType;
import br.com.orion.cursospring.model.Student;
import br.com.orion.cursospring.repository.StudentRepository;


/**
 * StudentEndPoint
 * 
 * @param <TesteUt>
 */
@RestController
@RequestMapping(path = "/students")
public class StudentEndPoint {
    
    private final StudentRepository studentRepository;
    @Autowired
    public StudentEndPoint(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(studentRepository.findAll(), HttpStatus.OK);
    }
    
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        Optional<Student> student = studentRepository.findById(id);

        if (student.get() == null) {
            return new ResponseEntity<>(new CustomErrorType("Student not found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(student.get(), HttpStatus.OK);
    }
    
    @GetMapping(path = "/name/{name}")
    public ResponseEntity<?>   findByName(@PathVariable String name){
        return new ResponseEntity<>(studentRepository.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Student student) {
        Student studentSaved = studentRepository.save(student);
        return new ResponseEntity<>(studentSaved, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        studentRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Student student) {
        studentRepository.save(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    

   
    
}