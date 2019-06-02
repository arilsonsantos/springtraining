package br.com.orion.school.resttemplate.get;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.orion.school.model.Student;
import lombok.extern.slf4j.Slf4j;

/**
 * GetObjectTest
 */
@Slf4j
public class GetForEntityTest {
    
    public static void main(String[] args) {
        getObjectStudentTest();
    }

    public static void getObjectStudentTest() {
        
        RestTemplate restTemplate = new RestTemplateBuilder()
        .rootUri("http://localhost:8080/orion/api/v1/protected/students").
        basicAuthentication("maria", "123").build();

        ResponseEntity<Student> entity = restTemplate.getForEntity("/{id}", Student.class, "1");

        log.info(entity.getBody().getName());

    }
    
}