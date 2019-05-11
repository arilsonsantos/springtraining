package br.com.orion.school.endpoint.student;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.orion.school.ApplicationStartTests;
import br.com.orion.school.model.Student;

/**
 * GetObjectTest
 */
public class GetForEntityTest extends ApplicationStartTests {

    @Test
    public void getObjectStudentTest() {
        RestTemplate restTemplate = new RestTemplateBuilder()
        .rootUri("http://localhost:8080/orion/api/students").
        basicAuthentication("maria", "123").build();

        ResponseEntity<Student> entity = restTemplate.getForEntity("/{id}", Student.class, "1");

        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }
    
}