package br.com.orion.school.endpoint;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.orion.school.error.ResourceNotFoundException;
import br.com.orion.school.model.Student;
import br.com.orion.school.repository.StudentRepository;

/**
 * StudentEndPoint
 * 
 * @param <TesteUt>
 */
@RestController
@RequestMapping(path = "v1")
public class StudentEndPoint {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentEndPoint(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping(path = "students/hello")
    public String hello(@AuthenticationPrincipal UserDetails userDetails) {
        System.out.println(userDetails);
        return "Hello " + userDetails.getUsername().toUpperCase();
        }

    @GetMapping(path = "protected/students")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(studentRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "admin/students/pages")
    public ResponseEntity<?> findAll(Pageable pageable) {
        return new ResponseEntity<>(studentRepository.findAll(pageable), HttpStatus.OK);
    }


    

    @GetMapping(path = "protected/students/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails user) {
        System.out.println(user);
        verifyIfStudentExists(id);
        Student student = studentRepository.findById(id).get();

        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping(path = "protected/students/name/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {
        return new ResponseEntity<>(studentRepository.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
    }

    @PostMapping(path = "admin/students")
    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<?> save(@Valid @RequestBody Student student) {
        Student studentSaved = studentRepository.save(student);
        return new ResponseEntity<>(studentSaved, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "admin/students/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        verifyIfStudentExists(id);
        studentRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "admin/students")
    public ResponseEntity<?> update(@Valid @RequestBody Student student) {
        verifyIfStudentExists(student.getId());
        studentRepository.save(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyIfStudentExists(Long id) {
        Optional<Student> student = studentRepository.findById(id);

        if (!student.isPresent()) {
            throw new ResourceNotFoundException("Student not found for ID: " + id);
        }
    }

}