package br.com.orion.school.endpoint;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import br.com.orion.school.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * StudentEndPoint
 * 
 * @param <TesteUt>
 */
@RestController
@RequestMapping(path = "v1")
@Api(description =  "Student resource")
public class StudentEndPoint {

    private final StudentRepository studentRepository;

    private final StudentService studentService;

    @Autowired
    public StudentEndPoint(StudentRepository studentRepository, StudentService studentService) {
        this.studentRepository = studentRepository;
        this.studentService = studentService;
    }
    

    @GetMapping(path = "students/hello")
    public String hello(@AuthenticationPrincipal UserDetails userDetails) {
        return "Hello " + userDetails.getUsername().toUpperCase();
        }

    @GetMapping(path = "protected/students")
    @ApiOperation(value = "Return a list with all students", response = Student[].class)
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(studentRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "admin/students/pages")
    public ResponseEntity<?> findAll(Pageable pageable) {
        return new ResponseEntity<>(studentRepository.findAll(pageable), HttpStatus.OK);
    }


    @GetMapping(path = "protected/students/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        verifyIfStudentExists(id);
        Student student = studentService.getById(id);

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
        
        Student student = studentService.getById(id);

        if (student == null) {
            throw new ResourceNotFoundException("Student not found for ID: " + id);
        }
    }

}