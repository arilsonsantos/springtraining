package br.com.orion.school.endpoint.student.get;

import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import br.com.orion.school.ApplicationStartTests;
import br.com.orion.school.model.Student;

/**
 * GetObjectTest
 */
public class GetObjectTest extends ApplicationStartTests {

    @Test
    public void getObjectStudentTest() {
        RestTemplate restTemplate = new RestTemplateBuilder()
        .rootUri("http://localhost:8080/orion/api/students").
        basicAuthentication("maria", "123").build();

        Student student = restTemplate.getForObject("/{id}", Student.class, "1");

        System.out.println(student.getName());
    }
    
}