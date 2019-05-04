package br.com.orion.cursospring.endpoint;

import java.time.LocalDateTime;

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
import br.com.orion.cursospring.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;


/**
 * StudentEndPoint
 * 
 * @param <TesteUt>
 */
@Slf4j
@RestController
@RequestMapping(path = "/students")
public class StudentEndPoint {
    
    private DateUtils dateUtils;

    @Autowired
    public StudentEndPoint(DateUtils dataUtil) {
        this.dateUtils = dataUtil;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        log.info(dateUtils.formatLocalDateTimeToDateBaseStyle(LocalDateTime.now()));
        return new ResponseEntity<>(Student.studantList, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        Student student = new Student();
        student.setId(id);
        int index = Student.studantList.indexOf(student);
        if (index == -1) {
            return new ResponseEntity<>(new CustomErrorType("Student not found"), HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(Student.studantList.get(index), HttpStatus.OK);
        
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Student student) {
        Student.studantList.add(student);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody Student student) {
        Student.studantList.remove(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Student student) {
        Student.studantList.remove(student);
        Student.studantList.add(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    

   
    
}